const express    = require('express');
const mysql      = require('mysql');
const dbconfig   = require('./config/database.js');
const connection = mysql.createConnection(dbconfig);

const app = express();
// function ==============================
function create_jobID()
{
  let datekey = new Date();
  let jobkey = '';
  var rand_key = Math.floor(Math.random()*10);
  let month = datekey.getMonth() % 10;
  let day = datekey.getDate() + 10;
  let hours = datekey.getHours() + 10;
  let minutes = datekey.getMinutes() + 10;
  let seconds = datekey.getSeconds() + 10;
  jobkey = jobkey + rand_key + month + day + hours + minutes + seconds;
  console.log('jobkey: ' + jobkey);
  return jobkey;
}

/*
function time_formating(time)
{
  let datetime = time.substring(0,8); //20180101_133050 -> '20180101 13:30:50'
  datetime += ' ';
  datetime += time.substring(9,11) + ':' + time.substring(11,13) + ':' + time.substring(13,15); 
  return datetime;
}
*/

// configuration =========================
app.set('port', process.env.PORT || 80);

app.get('/', (req, res) => {
  res.send('Root');
});

app.get('/debug/jobid', (req, res) => {
  res.send(create_jobID());
});

app.get('/debug/time/:time', (req, res) => {
  let {time} = req.params;
  let datetime = time_formating(time);
  res.send(datetime);
});

app.get('/debug/currenttime', (req, res) => {
  const curr = new Date();
  const utc = curr.getTime() + (curr.getTimezoneOffset()*60*1000);
  const KR_TIME_DIFF = 9*60*60*1000;
  const today = new Date(utc + KR_TIME_DIFF);
  
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1;  // 월
  let date = today.getDate();  // 날짜
  let hours = today.getHours(); // 시
  let minutes = today.getMinutes();  // 분
  let seconds = today.getSeconds();  // 초
  let servertime = '' + year + month + date + hours + minutes + seconds;

  res.send(servertime);
});

app.get('/api/users/:id', (req, res) => {
  let {id} = req.params;
  if(id == 'all')
  {
    var sql_user_all = 'SELECT * from Users';
    connection.query(sql_user_all, (error, results) => {
      if (error) throw error;
      console.log('User info is: ', results);
      res.send(JSON.stringify(results));
    });
  }
});

app.get('/api/users/:id/:nickname', (req, res) => {
  let {id, nickname} = req.params;
  if(id == 'all')
  {
    var sql_user_all = 'SELECT * from Users';
    connection.query(sql_user_all, (error, results) => {
      if (error) throw error;
      console.log('User info is: ', results);
      res.send(JSON.stringify(results));
    });
  }
  else
  {
    var sql_user_id_existness = 'select EXISTS(select * from Users where id='+id+' limit 1) as success';
    connection.query(sql_user_id_existness,(error, results) =>{
      if (error) throw error;
      var existSTR = JSON.stringify(results);
      if(!parseInt(existSTR[12]))
      {
        console.log('No exist');
        connection.query('INSERT INTO Users (id,nickname,count) VALUES (\''+id+'\', \''+nickname+ '\',0);', (error, ins_res) => {
          if (error) throw error;
            connection.query('select * from Users where id='+id,(error, results) => {
            if (error) throw error;
            res.send(JSON.stringify(results));
      });
        });
      }
      else
      {
        console.log('exist');
        connection.query('select * from Users where id='+id,(error, results) => {
          if (error) throw error;
          res.send(JSON.stringify(results));
      });
      }
    });
  }
});

app.get('/api/mypage/host/:userid', (req, res) => {
  let {userid} = req.params;
  let sql_user_host = 'select * from Partys where host=' + userid;
  connection.query(sql_user_host, (error, results) =>{
    if (error) throw error;
    res.send(JSON.stringify(results));
  });
  // userid가 host인 jobid 리턴
});

app.get('/api/mypage/participate/:userid', (req, res) => {
  let {userid} = req.params;
  let sql_user_participate = 'select * from Partys where Participant1=? OR Participant2=? OR Participant3=?';
  let params_user_participate = [userid, userid, userid];
  connection.query(sql_user_participate, params_user_participate, (error, results) =>{
    if (error) throw error;
    res.send(JSON.stringify(results));
  });
  // userid가 participate인 jobid 리턴
});

app.get('/api/partys/create/:userid/:category/:name/:maxjoin/:time', (req, res) => {
  // param을 바탕으로 id를 create하고, host에 userid를 넣은 후 JSON으로 row 전달
  let {userid, category, name, maxjoin, time} = req.params;
  let hostname;
  let jobid = create_jobID();
  //let datetime = time_formating(time);
  connection.query('select * from Users where id='+userid,(error, nickresults) => {
    if (error) throw error;
    hostname = nickresults[0].nickname;
    console.log(hostname);
    let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host, hostname) VALUES(?,?,?,?,?,?,?,?)';
    let params_party_create = [jobid, category, name, 1, parseInt(maxjoin), time, userid, hostname];
    connection.query(sql_party_create, params_party_create, (err, result, fields) => {
      if (err) throw err;
      let sql_party_info = 'select * from Partys where id='+jobid;
      connection.query(sql_party_info, (error, results, fields) => {
        if (error) throw error;
        console.log('Create :', results);
        res.send(JSON.stringify(results));
        let sql_count_inc = 'UPDATE Users set count = count + 1 where id = ' + userid;
        connection.query(sql_count_inc, (error, resultcnt, fieldcnt) =>{
          if (error) throw error;
          console.log("inc success");
        });
      });
    });
  });
});

app.get('/api/partys/participate/:userid/:jobid', (req, res) => {
  // jobid에 userid 등록
  let {userid, jobid} = req.params;
  let sql_party_show = 'select Joined,MAXjoin,host,Participant1,Participant2,Participant3 from Partys where id='+jobid;
  connection.query(sql_party_show, (error, results, fields) => {
    if(error) throw error;
    if(results[0].host == userid || results[0].Participant1 == userid || results[0].Participant2 == userid || results[0].Participant3 == userid)
    {
      console.log('already joined');
      res.send('already joined');
    }
    else if(results[0].Joined == results[0].MAXjoin)
    {
      console.log('err-FULL PARTY');
      res.send('err-FULL PARTY');
    }
    else
    {
      connection.query('select * from Users where id='+userid,(error, nickresults) => {
        if (error) throw error;
        hostname = nickresults[0].nickname;
        console.log(hostname);
        let sql_party_participate = 'UPDATE Partys SET Joined=?, Participant'+results[0].Joined+'=?, name'+results[0].Joined+'=? WHERE id=?';
        let params_party_participate = [results[0].Joined+1, userid, hostname, jobid];
        connection.query(sql_party_participate, params_party_participate, (error, presults, fields) =>
        {
          if(error) throw error;
          let sql_party_info = 'select * from Partys where id='+jobid;
          connection.query(sql_party_info, (error, iresults, fields) => {
            if (error) throw error;
            console.log('Participate :', iresults);
            res.send(JSON.stringify(iresults));
            let sql_count_inc = 'UPDATE Users set count = count + 1 where id = ' + userid;
            connection.query(sql_count_inc, (error, resultcnt, fieldcnt) =>{
              if (error) throw error;
              console.log("inc success");
            });
          });
        }); 
      });
    }
  })
});

app.get('/api/partys/delete/:userid/:jobid', (req, res) => {
  let {userid, jobid} = req.params;
  let sql_party_all = 'SELECT * from Partys WHERE id = ' + jobid; 
    connection.query(sql_party_all, (error, results) => {
      if (error) throw error;
      console.log('All Partys info is: ', results);
      let joined = results[0].Joined;
      let part1, part2, part3;
      let sql_party_delete = 'DELETE FROM Partys where id=? AND host=?';
      let params_party_delete = [jobid, userid];
      connection.query(sql_party_delete, params_party_delete, (err, result) =>
      {
        if (err) throw err;
        let del_cnt = result.affectedRows;
        if(del_cnt == 0)
        {
          console.log("Delete Failed");
          res.send("Delete Failed");  
        }
        else
        {
          console.log("Delete Successed");
          console.log('joined : ' + joined);
          res.send("Delete Successed");
          if(joined == 1)
          {
            let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ' + userid;
            connection.query(sql_count_dec, (error, resultcnt, fieldcnt) =>{
              if (error) throw error;
              console.log("dec success");
            });
          }
          else if(joined == 2)
          {
            part1 = results[0].Participant1;
            let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ? or id = ?';
            let params_count_dec = [userid, part1];
            connection.query(sql_count_dec, params_count_dec,(error, resultcnt, fieldcnt) =>{
              if (error) throw error;
              console.log("dec success");
            });

          }
          else if(joined == 3)
          {
            part1 = results[0].Participant1;
            part2 = results[0].Participant2;
            let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ? or id = ? or id = ?';
            let params_count_dec = [userid, part1, part2];
            connection.query(sql_count_dec, params_count_dec,(error, resultcnt, fieldcnt) =>{
              if (error) throw error;
              console.log("dec success");
            });
          }
          else if(joined == 4)
          {
            part1 = results[0].Participant1;
            part2 = results[0].Participant2;
            part3 = results[0].Participant3;
            let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ? or id = ? or id = ? or id = ?';
            let params_count_dec = [userid, part1, part2, part3];
            connection.query(sql_count_dec, params_count_dec,(error, resultcnt, fieldcnt) =>{
              if (error) throw error;
              console.log("dec success");
            });
          }  
        }
      })
    });
  // userid가 host인 경우 jobid 삭제
});

app.get('/api/partys/resign/:userid/:jobid', (req, res) => {
  let {userid, jobid} = req.params;
  let resign_idx;
  let sql_party_info = 'select * from Partys where id='+jobid;

  connection.query(sql_party_info, (error, results, fields) => {
    if (error) throw error;
    console.log(results);
    if(results[0].host == userid)
    {
      res.send("host cant resign");
    }
    else
    {
      //(id, Category, Name, Joined, MAXJoin, time, host)
      let new_id = results[0].id;
      let new_Category = results[0].Category;
      let new_Name = results[0].Name;
      let new_Joined = results[0].Joined - 1;
      let new_MAXJoin = results[0].MAXjoin;
      let new_time = results[0].time;
      let new_hos = results[0].host;
      let new_hostname = results[0].hostname;
      let new_Participant1;
      let new_Participant2;
      let new_name1;
      let new_name2;
      if(results[0].Participant1 == userid)
      {
        resign_idx = 1;
      }
      if(results[0].Participant2 == userid)
      {
        resign_idx = 2;
      }
      if(results[0].Participant3 == userid)
      {
        resign_idx = 3;
      }
      let sql_party_delete = 'DELETE FROM Partys where id=?';
      let params_party_delete = [jobid];
      connection.query(sql_party_delete, params_party_delete, (err, result) =>
      {
        if (err) throw err;
        let del_cnt = result.affectedRows;
        if(del_cnt == 0)
        {
          console.log("Delete Failed");
          res.send("Resign Failed");
        }
        else
        {
          console.log("Delete Successed");
          console.log(results[0].Joined);
          if(results[0].Joined == 2)
          {
            //only query
            let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host, hostname) VALUES(?,?,?,?,?,?,?,?)';
            let params_party_create = [new_id, new_Category, new_Name, new_Joined, new_MAXJoin, new_time, new_hos, new_hostname];
            connection.query(sql_party_create, params_party_create, (err, resultc, fields) => {
              if (err) throw err;
              console.log("2/n -> 2 resign");
              res.send("Resign Successed");
              let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ' + userid;
              connection.query(sql_count_dec, (error, resultcnt, fieldcnt) =>{
                if (error) throw error;
                console.log("dec success");
              });
            });
          }
          else if(results[0].Joined == 3)
          {
            if(resign_idx == 1)
            {
              new_Participant1 = results[0].Participant2;
              new_name1 = results[0].name2;
              let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host, hostname, Participant1, name1) VALUES(?,?,?,?,?,?,?,?,?,?)';
              let params_party_create = [new_id, new_Category, new_Name, new_Joined, new_MAXJoin, new_time, new_hos, new_hostname,new_Participant1, new_name1];
              connection.query(sql_party_create, params_party_create, (err, resultc, fields) => {
                if (err) throw err;
                console.log("3/n -> 2 resign");
                res.send("Resign Successed");
                let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ' + userid;
                connection.query(sql_count_dec, (error, resultcnt, fieldcnt) =>{
                  if (error) throw error;
                  console.log("dec success");
                });
              });
            }
            if(resign_idx == 2)
            {
              new_Participant1 = results[0].Participant1;
              new_name1 = results[0].name1;
              let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host, hostname, Participant1, name1) VALUES(?,?,?,?,?,?,?,?,?,?)';
              let params_party_create = [new_id, new_Category, new_Name, new_Joined, new_MAXJoin, new_time, new_hos, new_hostname, new_Participant1, new_name1];
              connection.query(sql_party_create, params_party_create, (err, resultc, fields) => {
                if (err) throw err;
                console.log("3/n -> 3 resign");
                res.send("Resign Successed");
                let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ' + userid;
                connection.query(sql_count_dec, (error, resultcnt, fieldcnt) =>{
                  if (error) throw error;
                  console.log("dec success");
                });
              });
            }
          }  
          else if(results[0].Joined == 4)
          {
            if(resign_idx == 1)
            {
              new_Participant1 = results[0].Participant3;
              new_name1 = results[0].name3;
              new_Participant2 = results[0].Participant2;
              new_name2 = results[0].name2;
              let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host, Participant1, Participant2, hostname, name1, name2) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)';
              let params_party_create = [new_id, new_Category, new_Name, new_Joined, new_MAXJoin, new_time, new_hos, new_Participant1, new_Participant2, new_hostname, new_name1, new_name2];
              connection.query(sql_party_create, params_party_create, (err, resultc, fields) => {
                if (err) throw err;
                console.log("4/n -> 2 resign");
                res.send("Resign Successed");
                let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ' + userid;
                connection.query(sql_count_dec, (error, resultcnt, fieldcnt) =>{
                  if (error) throw error;
                  console.log("dec success");
                });
              });
            }
            if(resign_idx == 2)
            {
              new_Participant1 = results[0].Participant1;
              new_name1 = results[0].name1;
              new_Participant2 = results[0].Participant3;
              new_name2 = results[0].name3;
              let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host, Participant1, Participant2, hostname, name1, name2) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)';
              let params_party_create = [new_id, new_Category, new_Name, new_Joined, new_MAXJoin, new_time, new_hos, new_Participant1, new_Participant2, new_hostname, new_name1, new_name2];
              connection.query(sql_party_create, params_party_create, (err, resultc, fields) => {
                if (err) throw err;
                console.log("4/n -> 3 resign");
                res.send("Resign Successed");
                let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ' + userid;
                connection.query(sql_count_dec, (error, resultcnt, fieldcnt) =>{
                  if (error) throw error;
                  console.log("dec success");
                });
              });
            }
            if(resign_idx == 3)
            {
              //only query
              new_Participant1 = results[0].Participant1;
              new_name1 = results[0].name1;
              new_Participant2 = results[0].Participant2;
              new_name2 = results[0].name2;
              let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host, Participant1, Participant2, hostname, name1, name2) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)';
              let params_party_create = [new_id, new_Category, new_Name, new_Joined, new_MAXJoin, new_time, new_hos, new_Participant1, new_Participant2, new_hostname, new_name1, new_name2];
              connection.query(sql_party_create, params_party_create, (err, resultc, fields) => {
                if (err) throw err;
                console.log("4/n -> 4 resign");
                res.send("Resign Successed");
                let sql_count_dec = 'UPDATE Users set count = count - 1 where id = ' + userid;
                connection.query(sql_count_dec, (error, resultcnt, fieldcnt) =>{
                  if (error) throw error;
                  console.log("dec success");
                });
              });
            }
          }    
        }
      })
    }
  });
});

app.get('/api/partys/show/:jobid', (req, res) => {
  let {jobid} = req.params;
  // jobid의 정보 보기
  if(jobid == 'all')
  {
    const curr = new Date();
    const utc = curr.getTime() + (curr.getTimezoneOffset()*60*1000);
    const KR_TIME_DIFF = 9*60*60*1000;
    const today = new Date(utc + KR_TIME_DIFF);
    
    let year = today.getFullYear(); // 년도
    let month = today.getMonth() + 1;  // 월
    let date = today.getDate();  // 날짜
    let _year, _month, _date;
    _year = year;
    if(month<10)
    {
      _month = '0'+month;
    }
    else
    {
      _month = month;
    }
    if(date<10)
    {
      _date = '0'+date;
    }
    else
    {
      _date = date;
    }
    let servertime = _year +'-'+ _month +'-'+ _date;
    console.log(servertime);
    let sql_party_all = 'SELECT * from Partys WHERE DATE(time) BETWEEN \'' + servertime + '\' AND' +'\'2099-01-23\' ORDER BY time ASC'; 
    connection.query(sql_party_all, (error, results) => {
      if (error) throw error;
      console.log('All Partys info is: ', results);
      res.send(JSON.stringify(results));
    });
  }
  else
  {
    let sql_party_info = 'select * from Partys where id='+jobid;
    connection.query(sql_party_info, (error, results, fields) => {
      if (error) throw error;
      console.log('Create :', results);
      res.send(JSON.stringify(results));
    });
  }
});

app.get('/api/partys/present', (req, res) => {
  // jobid의 정보 보기
    let sql_party_all = 'SELECT * from Partys ORDER BY time ASC';
    connection.query(sql_party_all, (error, results) => {
      if (error) throw error;
      console.log('All Partys info is: ', results);
      res.send(JSON.stringify(results));
    });
});

app.get('/api/partys/category/:category', (req, res) => {
  let {category} = req.params;
  let sql_party_category = 'select * from Partys where Category=?';
  let params_party_category = [category];
  connection.query(sql_party_category, params_party_category, (error, results) =>{
    if (error) throw error;
    res.send(JSON.stringify(results));
  });
});

app.listen(app.get('port'), () => {
  console.log('Express server listening on port ' + app.get('port'));
});
  
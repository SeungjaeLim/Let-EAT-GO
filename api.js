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

app.get('/api/users/:id', (req, res) => {
  let {id, email} = req.params;
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

app.get('/api/users/:id/:email', (req, res) => {
  let {id, email} = req.params;
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
        connection.query('INSERT INTO Users (id,email) VALUES (\''+id+'\', \''+email+ '\');', (error, ins_res) => {
          if (error) throw error;
        });
      }
      else
      {
        console.log('exist');
      }
      connection.query('select * from Users where id='+id,(error, results) => {
        if (error) throw error;
        res.send(JSON.stringify(results));
      });
    });
  }
});

app.get('/api/mypage/host/:userid', (req, res) => {
  let {userid} = req.params;
  let sql_user_host = 'select id from Partys where host=' + userid;
  connection.query(sql_user_host, (error, results) =>{
    if (error) throw error;
    res.send(JSON.stringify(results));
  });
  // userid가 host인 jobid 리턴
});

app.get('/api/mypage/participate/:userid', (req, res) => {
  let {userid} = req.params;
  let sql_user_participate = 'select id from Partys where Participant1=? OR Participant2=? OR Participant3=?';
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
  let jobid = create_jobID();
  //let datetime = time_formating(time);
  let sql_party_create = 'INSERT INTO Partys (id, Category, Name, Joined, MAXJoin, time, host) VALUES(?,?,?,?,?,?,?)';
  let params_party_create = [jobid, category, name, 1, parseInt(maxjoin), time, userid];
  connection.query(sql_party_create, params_party_create, (err, result, fields) => {
    if (err) throw err;
    let sql_party_info = 'select * from Partys where id='+jobid;
    connection.query(sql_party_info, (error, results, fields) => {
      if (error) throw error;
      console.log('Create :', results);
      res.send(JSON.stringify(results));
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
      let sql_party_participate = 'UPDATE Partys SET Joined=?, Participant'+results[0].Joined+'=? WHERE id=?';
      let params_party_participate = [results[0].Joined+1, userid, jobid];
      connection.query(sql_party_participate, params_party_participate, (error, presults, fields) =>
      {
        if(error) throw error;
        let sql_party_info = 'select * from Partys where id='+jobid;
        connection.query(sql_party_info, (error, iresults, fields) => {
          if (error) throw error;
          console.log('Participate :', iresults);
          res.send(JSON.stringify(iresults));
        });
      }); 
    }
  })
  
});

app.get('/api/partys/delete/:userid/:jobid', (req, res) => {
  let {userid, jobid} = req.params;
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
      res.send("Delete Successed");  
    }
  })
  // userid가 host인 경우 jobid 삭제
});

app.get('/api/partys/show/:jobid', (req, res) => {
  let {jobid} = req.params;
  // jobid의 정보 보기
  if(jobid == 'all')
  {
    let sql_party_all = 'SELECT * from Partys';
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

app.listen(app.get('port'), () => {
  console.log('Express server listening on port ' + app.get('port'));
});
  
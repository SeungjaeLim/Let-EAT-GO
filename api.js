const express    = require('express');
const mysql      = require('mysql');
const dbconfig   = require('./config/database.js');
const connection = mysql.createConnection(dbconfig);

const app = express();

// configuration =========================
app.set('port', process.env.PORT || 80);

app.get('/', (req, res) => {
  res.send('Root');
});

app.get('/api/users/:id/:email', (req, res) => {
    let {id, email} = req.params;
    if(id == 'all')
    {
        connection.query('SELECT * from Users', (error, results) => {
            if (error) throw error;
            console.log('User info is: ', results);
            res.send(JSON.stringify(results));
        });
    }
    else
    {
        connection.query('select EXISTS(select * from Users where id='+id+' limit 1) as success',(error, results) =>{
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
                res.send(JSON.stringify(results));
            });
        });
    }
  });

app.listen(app.get('port'), () => {
    console.log('Express server listening on port ' + app.get('port'));
  });
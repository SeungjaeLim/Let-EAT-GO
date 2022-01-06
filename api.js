const express = require('express');
const app = express();

const server = app.listen(80, () => {
    console.log('Start Server : PORT : 80');
});

app.get('/', async(req, res) =>{
    res.send('Default')
});
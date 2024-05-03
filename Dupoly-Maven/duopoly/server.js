const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors());

var r = (Math.random() + 1).toString(36).substring(4,10);

app.use('/', (req, res) => {
    res.send({
      token: r
    });
  });


  const host = '0.0.0.0'

  app.listen(8090, host, () => console.log('API is running on http://localhost:8090/login'));

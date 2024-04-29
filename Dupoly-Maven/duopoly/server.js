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

  app.listen(8080, () => console.log('API is running on http://localhost:8080/login'));

const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors());

app.use('/', (req, res) => {
    var r = (Math.random() + 1).toString(36).substring(4,10);
    res.send({
      token: r
    });
  });

  app.listen(8090, () => console.log('API is running on http://localhost:8090/login'));

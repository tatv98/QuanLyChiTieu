const express = require('express')
const app = express()
const bodyParser = require('body-parser')
const env = require('dotenv').config()
const port = process.env.PORT || 3000

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))

// app.get("/", (req, res) => {
//     res.json({ message: "Welcome my api app note" });
// });
let routes = require('./api/routes') //importing route
routes(app)

app.use(function (req, res) {
    res.status(404).send({ url: req.originalUrl + ' not found' }) // middleware ton tai req
})

app.listen(port)

console.log('RESTful API server started on: ' + port)
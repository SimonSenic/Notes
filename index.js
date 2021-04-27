const express = require('express')
const mongodb = require('mongodb')
const dateFormat = require('dateformat');
const app = express()
const MongoClient = mongodb.MongoClient
const connection = 'mongodb://localhost:27017'
const database = 'myfirstdb'

app.use(express.urlencoded({
    extended: true
}))

app.use(express.json())

app.get('', (req, res) => {
    res.send("Hello world!!")
})

app.get('/about', (req, res) => {
    res.send("<h1>Server</h1>")
})

app.get('/author', (req, res) => {
    res.send({"fName":"Šimon", "lName":"Senič"})
})

app.get('/notes', (req, res) => {
    MongoClient.connect(connection, {useNewUrlParser: true}, (error, client) => {
        if(error) return console.log("Invalid connection")
        let filter={}
        if(req.query.done == 'true'){
            if(req.query.priority == 1) filter={done: true, priority: 1}
            else if(req.query.priority == 2) filter={done: true, priority: 2}
            else if(req.query.priority == 3) filter={done: true, priority: 3}
            else filter={done: true}
        }
        else{
            if(req.query.priority == 1) filter={done: false, priority: 1}
            else if(req.query.priority == 2) filter={done: false, priority: 2}
            else if(req.query.priority == 3) filter={done: false, priority: 3}
            else filter={done: false}
        }
        const db = client.db(database)
        db.collection('notes').find(filter).toArray((err, result) => {
            if(err) throw err
            res.send(result)
        })
    })

})

app.get('/note', (req, res) => {
    MongoClient.connect(connection, {useNewUrlParser: true}, (error, client) => {
        if(error) return console.log("Invalid connection")
        var title = req.query.title
        const db = client.db(database)
        db.collection('notes').find({"title": {'$regex': '\w*' +title +'\w*', '$options': 'i'}}).toArray((err, result) => {
            if(err) throw err
            res.send(result)
        })
    })
})

app.post('/note/new', (req, res) => {
    MongoClient.connect(connection, (error, client) => {
        if(error) return console.log("Invalid connection")
        var currentDate = dateFormat(new Date(), "dd.mm.yyyy");
        var obj = { date: currentDate, title: req.body.title, task: req.body.task,
             priority: req.body.priority, price: req.body.price, done: false }
        const db = client.db(database)
        db.collection('notes').insertOne(obj, (err, result) => {
            if(err) throw err
            res.send({"Info":"Insert successfull"})
        })
    })

})

app.patch('/note/done', (req, res) => {
    MongoClient.connect(connection, (error, client) => {
        if(error) return console.log("Invalid connection")
        const db = client.db(database)
        db.collection('notes').updateOne({'title':req.query.title}, {$set: {'done':true}}, (err, result) => {
            if(err) throw err
            res.send({"Info":"Update succesfull"})
        })
    })
})

app.delete('notes/clear', (req, res) => {
    MongoClient.collection(connection, (error, client) => {
        if(error) return console.log("Invalid connection")
        const db = client.db(database)
        db.collection('notes').delete({'done':true}, (err, result) => {
            if(err) throw err
            res.send({"Info":"Delete successfull"})
        })
    })
})

app.listen(3000, () => {
    console.log("Server is running. ")
})
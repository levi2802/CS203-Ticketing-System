const express = require('express');
const MongoClient = require('mongodb').MongoClient;
const cors = require('cors');

const app = express();
app.use(cors());

const uri = "mongodb+srv://kenghiang3e42016:cs123@cluster0.w20a1hc.mongodb.net/";
const client = new MongoClient(uri, { useNewUrlParser: true, useUnifiedTopology: true });

app.get('/sse', (req, res) => {
    console.log("Hello");
    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Connection', 'keep-alive');
    res.flushHeaders();

    const collection = client.db("ticketing-api-db").collection("seats");
    const changeStream = collection.watch();

    changeStream.on('change', (change) => {
        res.write(`data: ${JSON.stringify(change)}\n\n`);
    });

    req.on('close', () => {
        changeStream.close();
    });
});

app.listen(3001, () => {
    console.log('Listening on port 3001');
});

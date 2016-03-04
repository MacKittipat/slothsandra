var cassandra = require('cassandra-driver');
var client = new cassandra.Client( { contactPoints : [ '127.0.0.1' ] } );
var elasticsearch = require('elasticsearch');

var eClient = new elasticsearch.Client({
  host: 'localhost:9200',
  log: 'error'
});

client.connect(function(err, result) {
  console.log('Connected.');
});

client.execute('SELECT * FROM slothsandra.message_by_channel', [], function(err, result) {
  if (err) {
    console.log('Error when select from message_by_channel', err);
  } else {
    console.log(result.rows.length);
    var bulkBody = [];
    for(var i=0; i<result.rows.length; i++) {
      var doc = result.rows[i];

      var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
      var date = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
      var elasticDoc = {};
      elasticDoc.channel_name = doc.channel_name;
      elasticDoc.username = doc.username;
      elasticDoc.message = doc.message;
      elasticDoc.created_time = doc.created_time.getTime();
      var created_time = doc.created_time;
      elasticDoc.created_year = created_time.getFullYear();
      elasticDoc.created_month = months[created_time.getMonth()];
      elasticDoc.created_day_of_week = date[created_time.getDay()];
      elasticDoc.created_hour = created_time.getHours();

      bulkBody.push({ index:  { _index: 'slothsandra', _type: 'slothmessage'} });
      bulkBody.push(elasticDoc);
    }

    // console.log(bulkBody);

    eClient.bulk({
      body: bulkBody
    }, function (err, resp) {
      console.log('ERROR : ' + err);
      // console.log(resp);
    });
  }
});

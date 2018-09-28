const axios = require('axios');
const sleep = require('system-sleep');

let id = 0

process.on('message', (msg) => {
    id = msg.id
})

function randomStr(m) {
	var m = m || 9; s = '', r = 'ABC';
	for (var i=0; i < m; i++) { s += r.charAt(Math.floor(Math.random()*r.length)); }
	return s;
};

setInterval(() => {
    const resource = randomStr(1)    
    axios.post('http://127.0.0.1:8080/api/request-resource', { resource: { name: resource }, process: { id: id } } )
      .then(response => {
        if (response.status === 200) {
          console.log('Accepted request to consume resource: ' + resource + ' from id: ' + id)
          sleep(5000) // Processing time
        
          axios.post('http://127.0.0.1:8080/api/release-resource', { name: resource } )
          .then(response => { 
            console.log('Resource: ' + resource + ' from id: ' + id + ' FREE')
          })
        }
    })
}, 10000) // Interval to consume resources
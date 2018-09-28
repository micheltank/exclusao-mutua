const { fork } = require('child_process')

let counter = 0

setInterval(() => {
    console.log('creating new process')
    const forked = fork('client.js')
    counter++
    forked.send({ id: counter })
}, 40000) // Interval to create new process
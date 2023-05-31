/// <reference lib="webworker" />

import * as Forge from 'node-forge'

addEventListener('message', ({ data }) => {
    var hash = data.hash;

    var response = data.response;
    var start = data.start;
    var end = data.end;
    var payloadEnd = data.payloadEnd;
    var sliceSize = data.sliceSize;

    const arrayBuffer = new ArrayBuffer(sliceSize);
    const int8Array = new Uint8Array(arrayBuffer);

    console.log("Slicing " + start + " " + end);
    if(end > payloadEnd){
        end = payloadEnd;
    }
    response.slice(start, end).text().then(value => {
        var data = Forge.util.decode64(value);
        hash.update(data);
        
        for (let i = 0; i < data.length; i++) {
            int8Array[i] = data.charCodeAt(i);
        }

        var messageData = {
            rawdata : data,
            blob : new Blob([int8Array])
        };

        postMessage(messageData);
    });
});

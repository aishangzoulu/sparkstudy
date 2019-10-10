app.title = '热力图与百度地图扩展';


var data = [[
    {"coord": [116.369, 39.9317], "elevation": 8108},
    {"coord": [117.188, 39.1372], "elevation": 2724},
    {"coord": [115.661, 38.6138], "elevation": 4212},
    {"coord": [112.515, 37.8666], "elevation": 4089},
    {"coord": [114.416, 43.4682], "elevation": 1414},
    {"coord": [122.754, 41.6216], "elevation": 3170},
    {"coord": [126.263, 43.6788], "elevation": 1849},
    {"coord": [128.047, 47.3566], "elevation": 2943},
    {"coord": [121.46, 31.2302], "elevation": 4860},
    {"coord": [119.368, 33.0138], "elevation": 9930},
    {"coord": [119.957, 29.1595], "elevation": 9267},
    {"coord": [117.216, 31.8593], "elevation": 6640},
    {"coord": [117.985, 26.0501], "elevation": 3791},
    {"coord": [115.676, 27.7573], "elevation": 4157},
    {"coord": [118.528, 36.0993], "elevation": 8588},
    {"coord": [113.487, 34.1572], "elevation": 7613},
    {"coord": [112.411, 31.2093], "elevation": 6835},
    {"coord": [111.721, 27.6959], "elevation": 4951},
    {"coord": [113.395, 23.408], "elevation": 12544},
    {"coord": [108.924, 23.5523], "elevation": 1658},
    {"coord": [109.734, 19.1805], "elevation": 528},
    {"coord": [106.445, 29.5905], "elevation": 3395},
    {"coord": [104.078, 30.6505], "elevation": 7906},
    {"coord": [106.735, 26.9028], "elevation": 1354},
    {"coord": [101.593, 24.8642], "elevation": 1520},
    {"coord": [91.1343, 29.6524], "elevation": 95},
    {"coord": [109.504, 35.86], "elevation": 5436},
    {"coord": [102.458, 38.1033], "elevation": 1556},
    {"coord": [101.784, 36.6091], "elevation": 346},
    {"coord": [106.155, 37.3213], "elevation": 458},
    {"coord": [87.6038, 43.7912], "elevation": 860},
    {"coord": [120.998, 23.9419], "elevation": 121},
    {"coord": [114.186, 22.2936], "elevation": 106},
    {"coord": [113.558, 22.2041], "elevation": 25}
]];
var points = [].concat.apply([], data.map(function (track) {
    return track.map(function (seg) {
        return seg.coord.concat([1]);
    });
}));
myChart.setOption(option = {
    animation: false,
    bmap: {
        center: [120.13066322374, 30.240018034923],
        zoom: 5,
        roam: true
    },
    visualMap: {
        show: false,
        top: 'top',
        min: 0,
        max: 5,
        seriesIndex: 0,
        calculable: true,
        inRange: {
            color: ['blue', 'blue', 'green', 'yellow', 'red']
        }
    },
    series: [{
        type: 'effectScatter',
        coordinateSystem: 'bmap',
        data: points,
        pointSize: 1,
        blurSize: 6
    }]
});
if (!app.inNode) {
    // 添加百度地图插件
    var bmap = myChart.getModel().getComponent('bmap').getBMap();
    bmap.addControl(new BMap.MapTypeControl());
}

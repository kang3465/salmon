
var dmp = new diff_match_patch();

function launch() {
    var text1 = document.getElementById('text1').value;
    var text2 = document.getElementById('text2').value;
    dmp.Diff_Timeout = parseFloat(document.getElementById('timeout').value);
    dmp.Diff_EditCost = parseFloat(document.getElementById('editcost').value);

    var ms_start = (new Date()).getTime();
    var d = dmp.diff_main(text1, text2);
    var ms_end = (new Date()).getTime();

    // if (document.getElementById('semantic').checked) {
    dmp.diff_cleanupSemantic(d);
    // }
    // if (document.getElementById('efficiency').checked) {
    //     dmp.diff_cleanupEfficiency(d);
    // }
    var ds = dmp.diff_prettyHtml(d);
    document.getElementById('outputdiv').innerHTML = ds + '<BR>Time: ' + (ms_end - ms_start) / 1000 + 's';
}


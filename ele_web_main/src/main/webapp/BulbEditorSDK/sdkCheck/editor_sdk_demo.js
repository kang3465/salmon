var L = window.console;
var editor;
var editorCheck = document.getElementById('editor-check');
var appKey = document.getElementById('app-key');
var appSecret = document.getElementById('app-secret');

editorCheck.addEventListener('click', function() {
    window.authBulbEditor({
        url: 'http://updateinfo.youdao.com/editorapi',
        pkn: 'com.youdao.com',
        appKey: appKey.value,
        version: 'v1',
        sdkVersion: 'v1',
        appSecret: appSecret.value,
    })
    .then(function(BulbEditor) {
        if (BulbEditor) {
            editor = new BulbEditor({
                el: document.getElementById('editor-container'),
                disableInsertLink: true,
            });
        }
    })
    .catch(function() {
        L.log('authorized failed!');
    });
});

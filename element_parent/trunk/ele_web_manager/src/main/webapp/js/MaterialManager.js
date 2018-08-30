var materialManager = new Vue({
    el: 'body',
    data: {
        entity:{},
        userList: {},
        pageNum:1,
        pageSize:10
    },
    methods: {

        loadUserList: function () {
            this.$http.post({
                url: "../user/queryUserListSafeByPage.do?pageNum="+this.pageNum+"&pageSize="+this.pageSize
            }).then(function (result) {
                this.userList = result.data;
            });
            /* for (var i = 0; i < this.userList.length; i++) {
                 if (this.userList[i].menuList.length == 0) {
                     this.userList[i].zclass = "collapse";
                 } else {
                     this.userList[i].zclass = "";
                 }

             }*/
        },
        uploadSectionFile: function (param) {
            var formData = new FormData();
            formData.append('id', this.ID);
            formData.append('file', this.file);
            $http({
                method: 'post',
                url: '../upload/uploadFile.do',
                data: formData,
                headers: {'Content-Type': undefined},// Content-Type : text/html  text/plain
                transformRequest: angular.identity

            });
        }
    }
});
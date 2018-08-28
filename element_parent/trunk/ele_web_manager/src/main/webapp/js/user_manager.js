var userManager = new Vue({
    el: 'body',
    data: {
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
        },
        opentable: function (even) {
            this.mainpage=even.currentTarget.toString();
            /*if ($("a[target='_blank']") == "collapse") {
                this.class0 = "collapse in";
            } else {
                this.class0 = "collapse";
            }*/
        },
        upload: function () {
            var param = new FormData(); // FormData 对象
            param.append("file", this.uploadFile); // 文件对象
            param.append("name", "ziguiyu"); // 其他参数
            this.$axios({
                method: "post",
                url: "./upload/uploadFile.do",
                data: param
            })
                .then(response => {
                    this.$message({
                        message: '上传成功',
                        type: 'success'
                    });
                })
                .catch(error => {
                    this.$message.error("上传失败,请稍后重试");
                });
        }
    }
});
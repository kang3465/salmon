var a = new Vue({
    el: 'body',
    data: {
        leftMenu: [],
        mainpage: "page/type_template.html",
        uploadFile: "",
        fileData: [],
        filename: "",
        class0: "collapse",
        class1: "collapse in"

    },
    methods: {
        SetIFrameHeight: function () {
            var iframeid = document.getElementById("iframe"); //iframe id
            if (document.getElementById) {
                iframeid.height = document.documentElement.clientHeight-50;
            }

        },
        loadMenu: function () {
            this.$http.get({
                url: "./menu/queryMenu.do"
            }).then(function (result) {
                this.leftMenu = result.data;
            });
            for (var i = 0; i < this.leftMenu.length; i++) {
                if (this.leftMenu[i].menuList.length == 0) {
                    this.leftMenu[i].class = "collapse";
                } else {
                    this.leftMenu[i].class = "";
                }

            }
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
            this.mainpage=even.currentTarget;
            /*if(this.leftMenu[index].class=="collapse"){
                this.leftMenu[index].class="collapse in"
            }else if (this.leftMenu[index].class=="collapse in") {
                this.leftMenu[index].class="collapse"
            }else{

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
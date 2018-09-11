var jqgridManager = new Vue({
    el: 'body',
    data: {
        materialEntity:{},
        materialList:[],
        selectIds:[],
        userList: {},
        pageNum:1,
        pageSize:10
    },
    methods: {
        updateSelection:function($event,id){
            // 复选框选中
            if($event.target.checked){
                // 向数组中添加元素
                this.selectIds.push(id);
            }else{
                // 从数组中移除
                var idx = this.selectIds.indexOf(id);
                this.selectIds.splice(idx,1);
            }
        },
        loadMaterialList: function () {
            this.$http.post({
                url: "../material/queryByPage.do?pageNum="+this.pageNum+"&pageSize="+this.pageSize
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
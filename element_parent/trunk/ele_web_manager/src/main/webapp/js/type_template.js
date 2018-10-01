Vue.filter('jsonFilter',{read:function(input){
    // 将字符串转成JSOn:
        alert(input)
    var jsonObj = JSON.parse(input);

    var value = "";
    for(var i=0;i<jsonObj.length;i++){

        if(i>0){
            value += ",";
        }

        value += jsonObj[i]["text"];
    }
    return value;
},write:function(input){ //view -> model
        // 将字符串转成JSOn:
        var jsonObj = JSON.parse(input);

        var value = "";
        for(var i=0;i<jsonObj.length;i++){

            if(i>0){
                value += ",";
            }

            value += jsonObj[i]["text"];
        }
        return value;
    }
})

var typetemplateManager = new Vue({
    el: '#typetemplate',
    data: {
        entity: {
            id: 0,
            name:"",
            specIds:[],
            brandIds:[],
            customAttributeItems:[],

        },emptyEntity: {
            id: 0,
            name:"",
            specIds:[],
            brandIds:[],
            customAttributeItems:[],

        },
        brandList:[],
        specList:[],
            list: [],
        selectIds: [],
        grade: 1,
        pageNum: 1,
        pageSize: 10

    },
    methods: {
        jsonToString:function(jsonStr,key){
            // 将字符串转成JSOn:
            var jsonObj = JSON.parse(jsonStr);

            var value = "";
            for(var i=0;i<jsonObj.length;i++){

                if(i>0){
                    value += ",";
                }

                value += jsonObj[i][key];
            }
            return value;
        },
        dele:function(){
            this.$http({
                url:"",
            })
        },addTableRow:function(){
            this.entity.customAttributeItems.push({});
        },deleteTableRow:function(index){
            this.entity.customAttributeItems.splice(index,1)
        },
        findOne: function (id) {
            this.$http.get({
                url: "../materialTypeTemplate/queryOne.do?id=" + id
            }).then(function (result) {
                this.entity.id= result.data.id;
                this.entity.name=result.data.name;
                // console.log(this.entity);
                var parse = JSON.parse(result.data.specIds);
                for(var i=0;i<parse.length;i++){
                    this.entity.specIds.push(parse[i])
                }
                parse = JSON.parse(result.data.brandIds);

                for(var i=0;i<parse.length;i++){
                    this.entity.brandIds.push(parse[i])
                }
                parse = JSON.parse(result.data.customAttributeItems);
                for(var i=0;i<parse.length;i++){
                    this.entity.customAttributeItems.push(parse[i])
                }
            });
        },
        loadList: function () {
            this.$http.get({
                url: "../materialTypeTemplate/queryAllByPage.do?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize
            }).then(function (result) {
                this.list = result.data.rows;
            });
        },deleteTableRow:function(index){
            this.entity.customAttributeItems.splice(index,1)
        },
        updateSelection: function ($event, id) {
            // 复选框选中
            if ($event.target.checked) {
                // 向数组中添加元素
                this.selectIds.push(id);
            } else {
                // 从数组中移除
                var idx = this.selectIds.indexOf(id);
                this.selectIds.splice(idx, 1);
            }
        }
    }, created:function(){
        this.loadList();
    }
});
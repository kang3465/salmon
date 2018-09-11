var materialCatManager = new Vue({
    el: 'body',
    data: {
        entity:{},
        entity_1:{},
        entity_2:{},
        list: [],
        grade:1,
        pageNum: 1,
        pageSize: 10

    },
    methods: {
        setGrade:function(grade){
        this.grade=grade;
},
        loadCat: function () {
            this.$http.get({
                url: "../category/queryCategoryByParentID.do?parentID=0"
            }).then(function (result) {
                this.list = result.data;
            });
        },
        selectList:function(p_entity){
            debugger;
            if(this.grade == 1){
                this.entity_1 = null;
                this.entity_2 = null;
            }
            if(this.grade == 2){
                this.entity_1 = p_entity;
                this.entity_2 = null;
            }
            if(this.grade == 3){
                this.entity_2 = p_entity;
            }

            this.$http.get({
                url: "../category/queryCategoryByParentID.do?parentID="+p_entity.id
            }).then(function (result) {
                this.list = result.data;
            });
        },

    },
    created:function () {
        this.loadCat();
    }
});
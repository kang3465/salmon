var a = new Vue({
    el: 'body',
    data: {
        entity: {
            specification: {
                id: 0,
                specName: "",
                specType: "",
            },
            specificationOptionList:[{
                id:0,
                optionName:"",
                specId:0,
                orders:0,
            }]

        },
        emptyEntity: {
            specification: {
                id: 0,
                specName: "",
                specType: "",
            },
            specificationOptionList:[{
                id:0,
                optionName:"",
                specId:0,
                orders:0,
            }]

        },
        list: [],
        selectIds: [],
        grade: 1,
        pageNum: 1,
        pageSize: 10

    },
    methods: {
        save:function(){
            var URL="";

            if (this.entity.specification.id!=null){
                URL="../specification/saveSpecification.do";
            }else {
                URL="../specification/addSpecification.do";
            }
            this.$http({
                method:"POST",
                url:URL,
                data:this.entity,
            }).then(function (res) {
                alert(res.data.message);
                this.loadSpecification();
            })

        },
        dele:function(){
            this.$http({
                url:"../specification/deleteSpecificationByIDs.do?ids="+this.selectIds,
            }).then(function (res) {
                alert(res.data.message);
                this.loadSpecification();
            })
        },
        addTableRow:function(){
            this.entity.specificationOptionList.push({});
        },deleteTableRow:function(index){
            this.entity.specificationOptionList.splice(index,1)
        },
        findOne: function (id) {
            this.$http.get({
                url: "../specification/queryOne.do?id=" + id
            }).then(function (result) {
                this.entity = result.data;
            });
        },
        loadSpecification: function () {
            this.$http.get({
                url: "../specification/querySpecificationAllByPage.do?pageNum=" + this.pageNum + "&pageSize=" + this.pageSize
            }).then(function (result) {
                this.list = result.data.rows;
            });
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
    }
});
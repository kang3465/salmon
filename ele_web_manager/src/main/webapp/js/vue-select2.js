Vue.directive('select2',{
    inserted: function (el, binding, vnode) {
        var options = binding.value || {};

        var defaultOpt = {
            placeholder: "--请选择--",
            allowClear: true
        };
        options = _.assign(defaultOpt, options);

        $(el).select2(options).on("select2:select", (e) => {
            // v-model looks for
            //  - an event named "change"
            //  - a value with property path "$event.target.value"
            el.dispatchEvent(new Event('change', { target: e.target })); //双向绑定不生效
            //绑定选中选项的事件
            options && options.onSelect && options.onSelect(e);
        });

        //allowClear:清除选中
        $(el).select2(options).on("select2:unselecting", (e) => {
            //清空这个值，这个值即vuejs model绑定的值
            e.target.value = "";
            el.dispatchEvent(new Event('change', {
                target: e.target
            })); //双向绑定不生效
        });
        console.log($(el));

        //绑定select2 dom渲染完毕时触发的事件
        options && options.onInit && options.onInit();
    },
    update: function (el, binding, vnode) {
        $(el).trigger("change");
    },
    bind:function () {
    console.log(this.el);
        this.el.select2({
            placeholder: 'Select an option'
        })
    }
});
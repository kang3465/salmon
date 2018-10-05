Vue.directive('select2',{
    bind: function (el, binding, vnode) {
        // 初始化
        var s = JSON.stringify
        debugger;
        console.log(this.el);
        console.log(el);
        console.log(binding);
        console.log(vnode);

        // var tagName = binding.value[0].tagName;
        // console.log(tagName);
    },
    inserted: function (el, binding, vnode) {
        var options = binding.value || {};
        debugger;
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
        // console.log($(el));

        //绑定select2 dom渲染完毕时触发的事件
        options && options.onInit && options.onInit();
    },
    update: function (el, binding, vnode) {
        debugger;
        // 初始化
        if (binding==undefined) {
            return;
        }
        var tagName = binding[0].text,config = {
            allowClear: true,
            multiple: !!el.attributes.getAttribute("multiple"),
            placeholder: el.attributes.placeholder || ' '   // 修复不出现删除按钮的情况
        };
        // 生成select
        if(tagName === 'SELECT') {
            // 初始化
            var $element = $(binding);
            delete config.multiple;

            angular.extend(config, scope.config);
            $element
                .prepend('<option value=""></option>')
                .val('')
                .select2(config);

            // model - view
            scope.$watch('ngModel', function (newVal) {
                setTimeout(function () {
                    $element.find('[value^="?"]').remove();    // 清除错误的数据
                    $element.select2('val', newVal);
                },0);
            }, true);
            return false;
        }
        // 处理input
        if(tagName === 'INPUT') {
            // 初始化
            var $element = $(element);

            // 获取内置配置
            if(attrs.query) {
                scope.config = select2Query[attrs.query]();
            }

            // 动态生成select2
            scope.$watch('config', function () {
                angular.extend(config, scope.config);
                $element.select2('destroy').select2(config);
            }, true);

            // view - model
            $element.on('change', function () {
                scope.$apply(function () {
                    scope.select2Model = $element.select2('data');
                });
            });

            // model - view
            scope.$watch('select2Model', function (newVal) {
                $element.select2('data', newVal);
            }, true);

            // model - view
            scope.$watch('ngModel', function (newVal) {
                // 跳过ajax方式以及多选情况
                if(config.ajax || config.multiple) { return false }

                $element.select2('val', newVal);
            }, true);
        }

        $(el).trigger("change");
    },componentUpdated:function (el, binding, vnode) {
        debugger;
    },unbind:function (el, binding, vnode) {
        debugger;
    }

});
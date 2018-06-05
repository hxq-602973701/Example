<#--
 所属模块：系统模块
 页面名称：单位列表
 创建时间：2018/05/29
 创建人员：lt
 -->

<!-- 样式 -->
<@override name="css">
<!-- 树形视图 -->
<link href="/assets/css/plugins/jsTree/style.min.css" rel="stylesheet">
<style>
	#txt-query:focus {
		border-color: #1C84C6 transparent #1C84C6 #1C84C6 !important;
	}
</style>
</@override>

<#-- 主体部分 -->
<@override name="main">
<div class="row">
	<div class="col-sm-3 col-md-3 col-lg-3 tree-div">
		<div class="ibox ">
			<div class="ibox-title" style="padding: 6px;">
				<h5 style="margin-left: 10px; margin-top: 8px;">组织机构</h5>
			</div>
			<div class="ibox-content">
				<!-- 左侧单位树 -->
				<div id="tree-dept"></div>
			</div>
		</div>
	</div>
	<div class="col-sm-9 col-md-9 col-lg-9 col-padding-left">
		<div class="ibox ">
			<div class="ibox-title" style="padding: 6px;">
				<h5 style="margin-left: 10px; margin-top: 8px;">单位列表</h5>
				</span>
			</div>
			<div class="ibox-content">
				<div class="bars pull-left col-sm-6" style="margin-bottom: 10px;padding-left:0px;">
					<div class="btn-group" role="group">
						<button type="button" class="btn btn-outline btn-success btn-insert">
							<i class="glyphicon glyphicon-plus"></i> 新增
						</button>
						<button type="button" class="btn btn-outline btn-danger btn-dels">
							<i class="glyphicon glyphicon-trash"></i> 删除
						</button>
					</div>
				</div>
				<div class="bars pull-right col-sm-6" style="margin-bottom: 10px;padding-right:0px;">
					<div class="input-group">
						<input type="text" id="txt-query" placeholder="单位名称、单位简称或单位编号。。。" class="form-control">
						<span class="input-group-btn">
                            <button type="button" class="btn btn-outline btn-success btn-query"
																		style="margin-left: 0"><i class="fa fa-search"></i> 搜索</button>
                        </span>
					</div>
				</div>
				<table id="tbl-dept" class="table table-bordered table-striped table-hover">
					<thead>
					<tr>
						<th style="width: 30px;">
							<input type="checkbox" id="chk-all"/>
							<label for="chk-all" class="check-box"></label>
						</th>
						<th class="u-table-center" style="width: 30px;">No.</th>
						<th class="u-table-center">单位编号</th>
						<th class="u-table-center">单位名称</th>
						<th class="u-table-center">单位简称</th>
						<th class="u-table-center">单位类型</th>
						<th class="u-table-center u-operation-4">操作</th>
					</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
				<div style="padding-top: 10px;" class="m-b-lg">
					<div id="page-info" class="pull-left"></div>
					<div id="page-bar" class="pull-right"></div>
				</div>
			</div>
		</div>
	</div>
</div>
</@override>

<#-- 脚本部分 -->
<@override name="scripts">
<!-- 树形视图 -->
<script src="/assets/js/plugins/jsTree/jstree.min.js"></script>
<!-- 系统单位行模版 -->
<script id="tpl-dept-row" type="text/html">
	{{# for(var i = 0, len = d.length; i < len; i++){ }}
	<tr>
		<td class="u-table-center">
			<input type="checkbox" id="chk-row-{{d[i]['deptId']}}" class="chk-row" value="{{ d[i]['deptId'] }}"/>
			<label for="chk-row-{{d[i]['deptId']}}" class="check-box"></label>
		</td>
		<td class="u-table-center">{{= i+1 }}</td>
		<td class="u-table-center">{{= d[i]['deptCode'] }}</td>
		<td class="u-table-center">{{= d[i]['deptName'] }}</td>
		<td class="u-table-center">{{= d[i]['deptShortName'] }}</td>
		<td class="u-table-center">{{= d[i]['deptTypeName'] }}</td>
		<td class="u-table-center">
			<button type="button" data-id="{{= d[i]['deptId'] }}" class="btn btn-sm btn-outline btn-primary btn-edit">编辑
			</button>
			<button type="button" data-id="{{= d[i]['deptId'] }}" class="btn btn-sm btn-outline btn-danger btn-del">删除
			</button>
		</td>
	</tr>
	{{# } }}
</script>
<script type="text/javascript">
	$(function () {

		// 当前选择的节点
		var selectedNode, pageing;

		// 树结构转化
		var builderTree = function (data, isFirst) {
		  //太长了显示不好看，所以去掉杭州市公安局
			if (data.deptList) {
				data.deptList.forEach(function (item, idx) {
					console.log(item);
					item.deptName = item.deptName.replace("杭州市公安局", "");
				});
			}
			var node = {text: data.deptName, icon: 'fa fa-flag', data: data};
			if (isFirst) {
				node.state = {selected: true};
				selectedNode = data;
			}
			isFirst && (node.state = {opened: true});
			if (data.deptList && data.deptList.length > 0) {
				var children = node.children || [];
				$.each(data.deptList, function (i, dept) {
					children.push(builderTree(dept, false))
				});
				node.children = children;
				if (children.length !== 0) {
					delete node['icon'];
				}
			}
			return node;
		};

		//构建树结构
		kirin.ajax({
			url: '/system/dept-tree.json',
		}).done(function (data) {

			$('#tree-dept').jstree({
				'core': {
					data: [builderTree(data, true)]
				}
			}).on('changed.jstree', function (e, data) {
				//设置当前选择的节点
				selectedNode = data.node.data;
				$('#txt-query').val('');
				pageing.init();
			});

			// 分页
			pageing = kirin.pageing(
				{
					view: '#tbl-dept',
					tpl: '#tpl-dept-row',
					noDataText: '此单位下暂无子单位',
					transform: function (data) {
						if (data) {
							$.each(data.list, function (idx, item) {
								item.deptTypeName = deptTypeTransform(item.deptType);
							});
						}
						return data;
					}
				},
				function (data) {
					data.deptParentId = selectedNode.deptId;
					data.query = $('#txt-query').val();
					return kirin.ajax({
						url: '/system/dept-page.json',
						data: data
					});
				}
			);

		});

		// 单位类型转换
		var deptTypeTransform = function (value) {
			switch (value) {
				case 1:
					return '公安局';
				case 16:
					return '公安局';
				case 32:
					return '市局';
				case 128:
					return '区分局';
				case 256:
					return '支队';
				case 1024:
					return '县分局';
				case 4096:
					return '机关单位';
				case 8192:
					return '派出所';
			}
		};

		// 新增按钮
		$('.btn-insert').on('click', function () {
			kirin.popup.open({
				title: '新增单位',
				width: 750,
				height: 440,
				maxmin: false,
				content: ['/system/dept.html?deptParentId=', selectedNode.deptId].join(''),
			}).done(function (data) {
				if (data === 'update') {
					pageing.reload();
				} else {
					pageing.init();
				}
			});
		});

		// 批量删除按钮
		$('.btn-dels').on('click', function () {

			//询问框
			layer.confirm('删除此单位可能导致系统<br>运行异常，请谨慎操作？',
				{
					icon: 0,
					title: '删除单位'
				},
				function () {
					var deptIds = [];

					//获取所有选中的单位ID
					$('.chk-row:checked').each(function () {
						deptIds.push(Number($(this).val()));
					});

					if (deptIds.length == 0) {
						layer.alert('请选择待删除的行。');
						return false;
					}

					// 删除
					kirin.ajax({
						type: 'DELETE',
						url: '/system/dept.json',
						data: {
							deptIds: deptIds
						}
					}).done(function () {
						pageing.reload();
						layer.msg('单位删除成功。', {icon: 1}, {time: 1500});
//                        layer.alert('单位删除成功。', {icon: 1});
					});
				}
			);
		});

		// 回车事件
		$('#txt-query').on('keydown', function (e) {
			if (e.keyCode === 13) {
				pageing.init();
			}
		});
		$('.btn-query').on('click', function () {
			pageing.init();
		});

		//全选/全不选
		$('#chk-all').on('click', function () {
			var checked = $(this).prop('checked');
			$('.chk-row').prop('checked', checked);
		});

		// 编辑按钮
		$('#tbl-dept').on('click', '.btn-edit', function () {
			var deptId = $(this).data('id');
			kirin.popup.open({
				title: '编辑单位',
				width: 750,
				height: 440,
				maxmin: false,
				content: ['/system/dept.html?deptId=', deptId, '&deptParentId=', selectedNode.deptId].join(''),
			}).done(function (data) {
				if (data === 'update') {
					pageing.reload();
				} else {
					pageing.init();
				}
			});
		});

		// 删除按钮
		$('#tbl-dept').on('click', '.btn-del', function () {
			$('.chk-row').prop('checked', false);
			var deptId = $(this).data('id');
			$('#chk-row-' + deptId).prop('checked', true);
			$('.btn-dels').trigger('click');
		});

	});
</script>
</@override>

<#-- 集成的Layout，必需在底部 -->
<@extends name="/common/layout/layout.ftl"/>



function swfl(){
	if(window.parent.mains.cols=="10,*"){
		document.getElementById("swfleft").className="swf_clo";
		document.getElementById("swfleft").innerHTML='<img src="' + dir_base + '/images/ydb/close.gif" alt="关闭左栏" border=0 />';
		document.getElementById("swf_menu").style.display=""
		window.parent.mains.cols="200,*";
	}else {
		document.getElementById("swfleft").className="swf_open";
		document.getElementById("swfleft").innerHTML='<img src="' + dir_base + '/images/ydb/open.gif" alt="打开左栏" border=0 />';
		document.getElementById("swf_menu").style.display="none"
		window.parent.mains.cols="10,*";
	}
}

function swfl1(){
	if(window.parent.mains.cols=="10,*"){
		document.getElementById("swfleft").className="swf_clo";
		document.getElementById("swfleft").innerHTML='<img src="' + dir_base + '/images/ydb/close.gif" alt="关闭左栏" border=0 />';
		window.parent.mains.cols="200,*";
		document.getElementById("swf_menu").style.display=""
	}else {
		document.getElementById("swfleft").className="swf_open";
		document.getElementById("swfleft").innerHTML='<img src="' + dir_base + '/images/ydb/open.gif" alt="打开左栏" border=0 />';
		window.parent.mains.cols="10,*";
		document.getElementById("swf_menu").style.display="none"
	}
}


var lastMenuId = null;
var lastMenuId_name = null;
function showl(j, j_name){
  try {
  	//alert("j=" + j + "," + "menu"+lastMenuId + "," + "menu"+j + "," + (document.getElementById("menu"+lastMenuId) != null) + "," + (document.getElementById("menu"+j) != null));
  	if(document.getElementById("menu"+lastMenuId) != null) {
  		//alert("hidden:\n" + document.getElementById("menu"+lastMenuId).outerHTML);
		document.getElementById("menu"+lastMenuId).style.display="none";  	
  	}
  	if(document.getElementById("menu"+j) != null) {
	  	//alert("show:\n" + document.getElementById("menu"+j).outerHTML);
		document.getElementById("menu"+j).style.display="block";  	
  	}
	lastMenuId = j;
  	if(j_name != null) {
  		lastMenuId_name = j_name;
  	}
  }catch(err){}
}

function showm(){
document.writeln('<table width="100%" border="0" cellpadding="2" cellspacing="0">');
document.writeln('	<tr><td align="center"><strong>张三</strong>，您好</td></tr>');
document.writeln('	<tr><td align="center">[<a href="../password.htm" target="contentFrame">修改密码</a>] [<a href="../login.html" target="_parent">退出登录</a>]</td></tr>');
document.writeln('	<tr><td align="center">频道：<select onChange="getIndustry();" name="selectedIndustry">');
document.writeln('	<option value="jewelry">珠宝首饰</option>');
document.writeln('	</select></td></tr></table>');
}

function showm1(){
document.writeln('<table width="100%" border="0" cellpadding="2" cellspacing="0">');
document.writeln('	<tr><td align="center"><strong>张三</strong>，您好</td></tr>');
document.writeln('	<tr><td align="center">[<a href="password.htm" target="contentFrame">修改密码</a>] [<a href="login.html" target="_parent">退出登录</a>]</td></tr>');
document.writeln('	<tr><td align="center">频道：<select onChange="getIndustry();" name="selectedIndustry">');
document.writeln('	<option value="jewelry">珠宝首饰</option>');
document.writeln('	</select></td></tr></table>');
}

function tree (a_items, a_template) {

	this.a_tpl      = a_template;
	this.a_config   = a_items;
	this.o_root     = this;
	this.a_index    = [];
	this.o_selected = null;
	this.n_depth    = -1;
	
	var o_icone = new Image(),
	o_iconl = new Image();
	o_icone.src = a_template['icon_e'];
	o_iconl.src = a_template['icon_l'];
	a_template['im_e'] = o_icone;
	a_template['im_l'] = o_iconl;
	for (var i = 0; i < 64; i++)
		if (a_template['icon_' + i]) {
			var o_icon = new Image();
			a_template['im_' + i] = o_icon;
			o_icon.src = a_template['icon_' + i];
		}
	
	this.toggle = function (n_id) {	var o_item = this.a_index[n_id]; o_item.open(o_item.b_opened) };
	this.select = function (n_id) { return this.a_index[n_id].select(); };
	this.mout   = function (n_id) { this.a_index[n_id].upstatus(true) };
	this.mover  = function (n_id) { this.a_index[n_id].upstatus() };

	this.a_children = [];
	for (var i = 0; i < a_items.length; i++)
		new tree_item(this, i);

	this.n_id = trees.length;
	trees[this.n_id] = this;
	
	for (var i = 0; i < this.a_children.length; i++) {
		document.write(this.a_children[i].init());
		this.a_children[i].open();
	}
}
function tree_item (o_parent, n_order) {

	this.n_depth  = o_parent.n_depth + 1;
	this.a_config = o_parent.a_config[n_order + (this.n_depth ? 2 : 0)];
	if (!this.a_config) return;

	this.o_root    = o_parent.o_root;
	this.o_parent  = o_parent;
	this.n_order   = n_order;
	this.b_opened  = !this.n_depth;

	this.n_id = this.o_root.a_index.length;
	this.o_root.a_index[this.n_id] = this;
	o_parent.a_children[n_order] = this;

	this.a_children = [];
	for (var i = 0; i < this.a_config.length - 2; i++)
		new tree_item(this, i);

	this.get_icon = item_get_icon;
	this.open     = item_open;
	this.select   = item_select;
	this.init     = item_init;
	this.upstatus = item_upstatus;
	this.is_last  = function () { return this.n_order == this.o_parent.a_children.length - 1 };
}

function item_open (b_close) {
	var o_idiv = get_element('i_div' + this.o_root.n_id + '_' + this.n_id);
	if (!o_idiv) return;
	
	if (!o_idiv.innerHTML) {
		var a_children = [];
		for (var i = 0; i < this.a_children.length; i++)
			a_children[i]= this.a_children[i].init();
		o_idiv.innerHTML = a_children.join('');
	}
	o_idiv.style.display = (b_close ? 'none' : 'block');

	this.b_opened = !b_close;
	var o_jicon = document.images['j_img' + this.o_root.n_id + '_' + this.n_id],
		o_iicon = document.images['i_img' + this.o_root.n_id + '_' + this.n_id];
	//if (o_jicon) o_jicon.src = this.get_icon(true);
	if (o_iicon) o_iicon.src = this.get_icon();
	this.upstatus();
}

function item_select (b_deselect) {
	if (!b_deselect) {
		var o_olditem = this.o_root.o_selected;
		this.o_root.o_selected = this;
		if (o_olditem) o_olditem.select(true);
	}
	var o_iicon = document.images['i_img' + this.o_root.n_id + '_' + this.n_id];
	if (o_iicon) o_iicon.src = this.get_icon();
	get_element('i_txt' + this.o_root.n_id + '_' + this.n_id).style.fontWeight = b_deselect ? 'normal' : 'normal';
	get_element('i_txt' + this.o_root.n_id + '_' + this.n_id).style.color = b_deselect ? '#000' : '#fff';
	get_element('i_txt' + this.o_root.n_id + '_' + this.n_id).style.backgroundColor = b_deselect ? '' : '#b00005';
	
	this.upstatus();
	return Boolean(this.a_config[1]);
}

function item_upstatus (b_clear) {
	window.setTimeout('window.status="' + (b_clear ? '' : this.a_config[0] + (this.a_config[1] ? ' ('+ this.a_config[1] + ')' : '')) + '"', 10);
}

function item_init () {
	var a_offset = [],
		o_current_item = this.o_parent;
	for (var i = this.n_depth; i > 1; i--) {
		a_offset[i] = '<img src="' + this.o_root.a_tpl[o_current_item.is_last() ? 'icon_e' : 'icon_l'] + '" border="0" align="absbottom">';
		o_current_item = o_current_item.o_parent;
	}
	if(this.a_config[1] == null)
	{
	return '<table cellpadding="0" cellspacing="0" border="0" width="100%" style="display:none"><tr><td nowrap height="20">' + (this.n_depth ? a_offset.join('')+ (this.a_children.length
		? '<a class="treelink" href="#" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')"><img src="' + this.get_icon(true) + '" border="0" align="absbottom" name="j_img' + this.o_root.n_id + '_' + this.n_id + '"></a>'
		: '<img src="' + this.get_icon(true) + '" border="0" align="absbottom">') : '') 
		+ '<img src="' + this.get_icon() + '" border="0" align="absbottom" name="i_img' + this.o_root.n_id + '_' + this.n_id + '" class="t' + this.o_root.n_id + 'im"><a class="title" href="#" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')">' + this.a_config[0] + '</a></td></tr></table>' + (this.a_children.length ? '<div id="i_div' + this.o_root.n_id + '_' + this.n_id + '" style="display:none;width:120;background-color:"></div>' : '');
	}
	if(o_current_item.a_config[1]==null&&this.n_depth ==1 && this.n_order == 0)
	{
	return '<table cellpadding="0" cellspacing="0" border="0"><tr><td nowrap height="20">' + (this.n_depth ? a_offset.join('') + (this.a_children.length
		? '<a class="treelink" href="javascript: trees[' + this.o_root.n_id + '].toggle(' + this.n_id + ')" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')"><img src="' + dir_base + '/images/ydb/tree/plus_1.gif" border="0" align="absbottom" name="j_img' + this.o_root.n_id + '_' + this.n_id + '"></a>'
		: '<img src="' + dir_base + '/images/ydb/tree/top.gif" border="0" align="absbottom">') : '') 
		+ '<a class="treelink" href="' + this.a_config[1] + '" onclick="return trees[' + this.o_root.n_id + '].select(' + this.n_id + ')" ondblclick="trees[' + this.o_root.n_id + '].toggle(' + this.n_id + ')" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')" class="t' + this.o_root.n_id + 'i" id="i_txt' + this.o_root.n_id + '_' + this.n_id + '"><img src="' + this.get_icon() + '" border="0" align="absbottom" name="i_img' + this.o_root.n_id + '_' + this.n_id + '" class="t' + this.o_root.n_id + 'im">' + this.a_config[0] + '</a></td></tr></table>' + (this.a_children.length ? '<div id="i_div' + this.o_root.n_id + '_' + this.n_id + '" style="display:none"></div>' : '');
	}
	return '<table cellpadding="0" cellspacing="0" border="0"><tr><td nowrap height="20">' + (this.n_depth ? a_offset.join('') + (this.a_children.length
		? '<a class="treelink" href="javascript: trees[' + this.o_root.n_id + '].toggle(' + this.n_id + ')" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')"><img src="' + this.get_icon(true) + '" border="0" align="absbottom" name="j_img' + this.o_root.n_id + '_' + this.n_id + '"></a>'
		: '<img src="' + this.get_icon(true) + '" border="0" align="absbottom">') : '') 
	  + '<img src="' + this.get_icon() + '" border="0" align="absbottom" name="i_img' + this.o_root.n_id + '_' + this.n_id + '" class="t' + this.o_root.n_id + 'im"><a class="treelink" href="' + this.a_config[1] + '" target="' + this.o_root.a_tpl['target'] + '" onclick="return trees[' + this.o_root.n_id + '].select(' + this.n_id + ')" ondblclick="trees[' + this.o_root.n_id + '].toggle(' + this.n_id + ')" onmouseover="trees[' + this.o_root.n_id + '].mover(' + this.n_id + ')" onmouseout="trees[' + this.o_root.n_id + '].mout(' + this.n_id + ')" class="t' + this.o_root.n_id + 'i" id="i_txt' + this.o_root.n_id + '_' + this.n_id + '">' + this.a_config[0] + '</a></td></tr></table>' + (this.a_children.length ? '<div id="i_div' + this.o_root.n_id + '_' + this.n_id + '" style="display:none"></div>' : '');
}


function item_get_icon (b_junction) {
	
	return this.o_root.a_tpl['icon_' + ((this.n_depth ? 0 : 32) + (this.a_children.length ? 16 : 0) + (this.a_children.length && this.b_opened ? 8 : 0) + (!b_junction && this.o_root.o_selected == this ? 4 : 0) + (b_junction ? 2 : 0) + (b_junction && this.is_last() ? 1 : 0))];
}

var trees = [];
get_element = document.all ?
	function (s_id) { return document.all[s_id] } :
	function (s_id) { return document.getElementById(s_id) };

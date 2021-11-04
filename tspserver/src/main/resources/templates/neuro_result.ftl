<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8"/>
	<link rel='stylesheet' href='${model["app_path"]}/resources/css/main.css'>
	<link rel='stylesheet' href='${model["app_path"]}/resources/css/menu.css'>
	<script src='${model["app_path"]}/resources/js/hcont.js'></script>
	<title>Блок чейн (владельцы блоков)</title>
</head>
<body>

<table>
	<th>Создан</th><th>Владелец</th>
<#list ${model["chain"]} as block>
	<td>block.ts</td><td>block.info</td>
</#list>
</table>
</body>
</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8"/>
	<title>Блок чейн (владельцы блоков)</title>
</head>
<body>

<table>
	<th></th><th>Создан</th><th>Владелец</th><th>ошибка</th>
<#list chain as block>
	<tr>
		<td>${block?counter}</td><td>${block.ts!}</td><td>${block.autor!}</td><td>${block.e!}</td>
	</tr>
</#list>
</table>
</body>
</html>
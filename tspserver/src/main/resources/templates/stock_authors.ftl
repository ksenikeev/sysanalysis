<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta charset="utf-8"/>
	<title>Блок чейн (владельцы блоков)</title>

	<style type="text/css">
	.stnorm {}
	.strisk {
		font-weight: bold;
		color: #FF0000;
	}
	.stsel {
		background-color: rgb(255, 255, 128);
	}
	</style>

</head>
<body>


<table style="border-spacing:10px;">
	<th></th><th>Создан</th><th>Владелец</th><th>Акция 1</th><th>Акция 2</th><th>Акция 3</th><th>Акция 4</th>
	<#list model["chain"] as block>
	<tr>
		<td>${block?counter}</td><td>${block.ts}</td><td>${block.data.name}</td>
		<td>${block.data.stock1}</td>
		<td>${block.data.stock2}</td>
		<td>${block.data.stock3}</td>
		<td>${block.data.stock4}</td>
	</tr>
	</#list>
</table>

</body>
</html>
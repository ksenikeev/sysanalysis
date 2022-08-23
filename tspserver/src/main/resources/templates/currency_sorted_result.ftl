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

<h3>Отсортированные данные</h3>

<table>
	<th></th><th>Создан</th><th>Владелец</th><th>Валюта 1</th><th>Валюта 2</th><th>Валюта 3</th><th>Валюта 4</th>
	<th>Стратегия</th>
	<th>Итог</th>
	<#list model["chain"] as block>
	<tr>
		<td>${block?counter}</td><td>${block.ts}</td><td>${block.name}</td>
		<td>${block.currency1}</td>
		<td>${block.currency2}</td>
		<td>${block.currency3}</td>
		<td>${block.currency4}</td>
		<td>${block.strategy!}</td>
		<td>${block.itog}</td>
	</tr>
</#list>
</table>

</body>
</html>
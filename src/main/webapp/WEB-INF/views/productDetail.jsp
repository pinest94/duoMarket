<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container-wrapper">
	<div class="container">
		<h2>All Products</h2>
		<p>착한 가격으로 상품을 살펴보세요!</p>
		<table class="table table-striped">
			<thead>
				<tr class="table-success">
					<th>Name</th>
					<th>Category</th>
					<th>Price</th>
					<th>Manufacturer</th>
					<th>UnitInStock</th>
					<th>Description</th>
					<th> </th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="product" items="${products}">
					<tr>
						<td>${product.name}</td>
						<td>${product.category}</td>
						<td><fmt:formatNumber value="${product.price}"
								type="currency" currencySymbol="￦" /></td>
						<td>${product.manufacturer}</td>
						<td><fmt:formatNumber value="${product.unitInStock}"
								pattern="#,###.##" /></td>
						<td>${product.description}</td>
						<td><a href="<c:url value="/productDetail/${product.id}"/>"><i class="fa fa-info-circle"></i></a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br><br>
		<!-- 상세 페이지 view -->
		<div class = "row">
			<div class="col-md-6">
				<c:set var="imageFilename" value="/resources/images/${product.id}.jpg" />
				<img src="<c:url value="${imageFilename}" />" alt="productImage" style="width: 80%"/>
				
			</div>
			
			<div class="col-md-6">
				<h3>${product.name}</h3>
				<p>상세정보 : ${product.description}</p>
				<p>제조사 : ${product.manufacturer}</p>
				<p>분류 : ${product.category}</p>
				<p>가격 : <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="￦" /></p>
				<p>재고 : <fmt:formatNumber value="${product.unitInStock}" pattern="#,###.##" /></p>
			</div>
		
		</div>
	</div>
</div>


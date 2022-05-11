<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-fQybjgWLrvvRgtW6bFlB7jaZrFsaBXjsOMm/tB9LTS58ONXgqbR9W8oWht/amnpF" crossorigin="anonymous"></script>
<style>
  .embed-responsive {
    border-radius: 12px;
  }
  .card{
    border : none;
  }
</style>
<title>펀딩 내역</title>
</head>
<body>
<c:import url="/header.do"></c:import> 
<main id="wrapper">

    <div class="container" style="background-color: white;">
        <div class="row">
            <!--펀딩&스토어&찜-->
            <div class="col-md-12 col-sm-12" style=" margin-top: 10%;">
                <!--펀딩내역-->
                <h3>펀딩 내역</h3>
                <div class="row">
                    <div class="col-sm" style="text-align: right">
                        <div class="dropdown">
                            <button class="btn btn-outline-info btn-lg dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown"
                                aria-expanded="false">
                                	전체
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton" id="dropdown">
                                <a class="dropdown-item" href="info_funding.do?funding_state=0">결제 예약</a>
                                <a class="dropdown-item" href="info_funding.do?funding_state=1">결제 완료</a>
                                <a class="dropdown-item" href="info_funding.do?funding_state=2">결제 실패</a>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <c:if test="${myFundingList.size()>0}">
				<c:forEach var="item" items="${myFundingList}">
	                <c:choose>
	                
	                	<c:when test="${ param.funding_state eq 0 }"> 
	                		<c:if test="${ item.funding_current_state eq 0 }">
	                		<div class="row fundingRow">
			                  <!--이미지-->
				                  <div class="col-md-4 col-sm-12" onclick="location.href='info_funding_detail.do?funding_idx=${item.funding_idx}'" style="cursor:pointer;">
				                      <div class="card">
				                          
				                            <div class="embed-responsive embed-responsive-4by3">
				                              <img src="../resources/image/funding_main/${ item.funding_thumbnail }" class="card-img-top embed-responsive-item" alt="tree">
				                            </div>
				                            
				                      </div>
				                  </div>
			                  <!--내용-->
			                  <div class="col-md-8 col-sm-12">
			                  
			                    <table class="table">
			                      <thead class="table-light" style="border-bottom: 2px solid gray;">
			                        <tr>
			                            <th colspan="2" style="text-align: center;">
			                                ${item.funding_title }
			                            </th>
			                        </tr>
			                      </thead>
			                      <tbody>
			                        <tr> 
			                            <th>펀딩 상태</th>
			                        </tr>
			                        <tr>
			                            <td style="border-top:none;"/">
			                            <td style="border-top:none;">
			                            	<c:choose>
			                            		<c:when test="${ item.funding_current_state == 0}">진행중</c:when>
			                            		<c:when test="${ item.funding_current_state == 1}">성공</c:when>
			                            		<c:otherwise>실패</c:otherwise>
			                            	</c:choose>
			                            </td>
			                        </tr>
			                        <tr> 
			                            <th style="border-top:none;">펀딩 금액</th>
			                        </tr>
			                        <tr>
			                            <td style="border-top:none;"/">
			                            <td style="border-top:none;">
											${ item.funding_current_price }/${ item.funding_target_price }
										</td>
			                        </tr>
			                      </tbody>
			                    </table>
			                  </div>         
			                </div>
			                <br>
	                	</c:if>
	                	
	                	</c:when>
	                	
	                	
	                	<c:when test="${ param.funding_state eq 1 }">
	                	
	                	<c:if test="${ item.funding_current_state eq 1 }">
	                		<div class="row fundingRow">
			                  <!--이미지-->
				                  <div class="col-md-4 col-sm-12" onclick="location.href='info_funding_detail.do?funding_idx=${item.funding_idx}'" style="cursor:pointer;">
				                      <div class="card">
				                          
				                            <div class="embed-responsive embed-responsive-4by3">
				                              <img src="../resources/image/funding_main/${ item.funding_thumbnail }" class="card-img-top embed-responsive-item" alt="tree">
				                            </div>
				                            
				                      </div>
				                  </div>
			                  <!--내용-->
			                  <div class="col-md-8 col-sm-12">
			                  
			                    <table class="table">
			                      <thead class="table-light" style="border-bottom: 2px solid gray;">
			                        <tr>
			                            <th colspan="2" style="text-align: center;">
			                                ${item.funding_title }
			                            </th>
			                        </tr>
			                      </thead>
			                      <tbody>
			                        <tr> 
			                            <th>펀딩 상태</th>
			                        </tr>
			                        <tr>
			                            <td style="border-top:none;"/">
			                            <td style="border-top:none;">
			                            	<c:choose>
			                            		<c:when test="${ item.funding_current_state == 0}">진행중</c:when>
			                            		<c:when test="${ item.funding_current_state == 1}">성공</c:when>
			                            		<c:otherwise>실패</c:otherwise>
			                            	</c:choose>
			                            </td>
			                        </tr>
			                        <tr> 
			                            <th style="border-top:none;">펀딩 금액</th>
			                        </tr>
			                        <tr>
			                            <td style="border-top:none;"/">
			                            <td style="border-top:none;">
											${ item.funding_current_price }/${ item.funding_target_price }
										</td>
			                        </tr>
			                      </tbody>
			                    </table>
			                  </div>         
			                </div>
			                <br>
	                	</c:if>
	                	
	                	</c:when>
	                	
	                	
	                	<c:when test="${ param.funding_state eq 2 }">
	                		<c:if test="${ item.funding_current_state eq 2 }">
	                		<div class="row fundingRow">
			                  <!--이미지-->
				                  <div class="col-md-4 col-sm-12" onclick="location.href='info_funding_detail.do?funding_idx=${item.funding_idx}'" style="cursor:pointer;">
				                      <div class="card">
				                          
				                            <div class="embed-responsive embed-responsive-4by3">
				                              <img src="../resources/image/funding_main/${ item.funding_thumbnail }" class="card-img-top embed-responsive-item" alt="tree">
				                            </div>
				                            
				                      </div>
				                  </div>
			                  <!--내용-->
			                  <div class="col-md-8 col-sm-12">
			                  
			                    <table class="table">
			                      <thead class="table-light" style="border-bottom: 2px solid gray;">
			                        <tr>
			                            <th colspan="2" style="text-align: center;">
			                                ${item.funding_title }
			                            </th>
			                        </tr>
			                      </thead>
			                      <tbody>
			                        <tr> 
			                            <th>펀딩 상태</th>
			                        </tr>
			                        <tr>
			                            <td style="border-top:none;"/">
			                            <td style="border-top:none;">
			                            	<c:choose>
			                            		<c:when test="${ item.funding_current_state == 0}">진행중</c:when>
			                            		<c:when test="${ item.funding_current_state == 1}">성공</c:when>
			                            		<c:otherwise>실패</c:otherwise>
			                            	</c:choose>
			                            </td>
			                        </tr>
			                        <tr> 
			                            <th style="border-top:none;">펀딩 금액</th>
			                        </tr>
			                        <tr>
			                            <td style="border-top:none;"/">
			                            <td style="border-top:none;">
											${ item.funding_current_price }/${ item.funding_target_price }
										</td>
			                        </tr>
			                      </tbody>
			                    </table>
			                  </div>         
			                </div>
			                <br>
	                	</c:if>
	                	
	                	</c:when>
	                	
	                	<c:otherwise>
	                	
	                	<div class="row fundingRow">
	                  <!--이미지-->
		                  <div class="col-md-4 col-sm-12" onclick="location.href='info_funding_detail.do?funding_idx=${item.funding_idx}'" style="cursor:pointer;">
		                      <div class="card">
		                          
		                            <div class="embed-responsive embed-responsive-4by3">
		                              <img src="../resources/image/funding_main/${ item.funding_thumbnail }" class="card-img-top embed-responsive-item" alt="tree">
		                            </div>
		                            
		                      </div>
		                  </div>
	                  <!--내용-->
	                  <div class="col-md-8 col-sm-12">
	                  
	                    <table class="table">
	                      <thead class="table-light" style="border-bottom: 2px solid gray;">
	                        <tr>
	                            <th colspan="2" style="text-align: center;">
	                                ${item.funding_title }
	                            </th>
	                        </tr>
	                      </thead>
	                      <tbody>
	                        <tr> 
	                            <th>펀딩 상태</th>
	                        </tr>
	                        <tr>
	                            <td style="border-top:none;"/">
	                            <td style="border-top:none;">
	                            	<c:choose>
	                            		<c:when test="${ item.funding_current_state == 0}">진행중</c:when>
	                            		<c:when test="${ item.funding_current_state == 1}">성공</c:when>
	                            		<c:otherwise>실패</c:otherwise>
	                            	</c:choose>
	                            </td>
	                        </tr>
	                        <tr> 
	                            <th style="border-top:none;">펀딩 금액</th>
	                        </tr>
	                        <tr>
	                            <td style="border-top:none;"/">
	                            <td style="border-top:none;">
									${ item.funding_current_price }/${ item.funding_target_price }
								</td>
	                        </tr>
	                      </tbody>
	                    </table>
	                  </div>         
	                </div>
	                <br>
	                	
	                	</c:otherwise>
	                </c:choose>
	                
	                
	             </c:forEach>
	             </c:if>
                
        	</div>
    	</div>
    </div>
</main>
<c:import url="/footer.do"></c:import>

    
</body>
</html>
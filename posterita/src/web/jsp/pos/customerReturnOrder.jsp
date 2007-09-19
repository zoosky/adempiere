<%--
 *  Product: Posterita Web-Based POS and Adempiere Plugin
 *  Copyright (C) 2007  Posterita Ltd
 *  This file is part of POSterita
 *  
 *  POSterita is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * @author Alok
--%>

<!-- customerReturnOrder.jsp -->
<%@ page import="org.posterita.user.*" %>
<%@ page import="org.posterita.Constants" %>


<%@ page import="org.posterita.struts.pos.POSOrderAction" %>
<%@ page import="org.posterita.beans.ProductBean" %>
<%@ page import="org.posterita.user.WebUserInfo" %>
<%@ page import="org.posterita.lib.UdiConstants" %>
<%@ page import="org.posterita.struts.pos.POSGoodsAction" %>
<%@ page import="org.posterita.order.UDIOrderTypes" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>	
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/dcs.tld" prefix="dcs" %>
<%@ taglib uri="/WEB-INF/pos.tld" prefix="pos"%>

<logic:present name="<%= Constants.ORDER_TITLE %>">
	<bean:define id="title"><bean:write name="<%= Constants.ORDER_TITLE %>"/></bean:define>
</logic:present>

<logic:notPresent name="<%= Constants.ORDER_TITLE %>">
	<bean:define id="title"><pos:element columnName="customer.returned.order" textOnly="true"/></bean:define>
</logic:notPresent>
<table width="100%">
	<td align="right">
		<html:link href="<%="CreateNewOrderAction.do?action=removeSessionForNewOrder&orderType=" + UDIOrderTypes.CUSTOMER_RETURN_ORDER.getOrderType() %>">
			Create New
		</html:link>			
	</td>
</table>
<%@ include file="/jsp/include/posHeader.jsp" %>
<%@ include file="/jsp/include/errors.jsp" %> 
	  						   
<html:form action="/CreateCustomerReturnOrderAction">
<html:hidden property="isSales" value="true"/>
<html:hidden property="action" value=""/>
<html:hidden property="ifAdd" value="true"/>
<html:hidden property="orderType" value="<%=Constants.CUSTOMER_RETURN_ORDER%>"/>

<logic:present name="<%= Constants.CUSTOMER_RETURN_ORDER_ID %>">
<bean:define id="orderId">
	<bean:write name="<%= Constants.CUSTOMER_RETURN_ORDER_ID %>"/>
</bean:define>
<html:hidden property="orderId" value="<%= orderId %>"/>
</logic:present>	

<table class="main" border="0">   
   	<tr>
   		<td>
	   		<table class="main" border="0">	   			
	   			<tr>
			   		<td>
			   			<label><pos:element columnName="barcode"/>:</label>
			   		</td>		
			   		<td>
			   			<html:text property="barCode" accesskey="b" styleClass="text"/>
			   			<html:hidden property="productId" value=""/>
			   		</td>		   		
			   		<td>
			   			<label><pos:element columnName="Qty"/>:</label>
			   		</td>		
			   		<td>
			   			<html:text property="quantity" accesskey="q" styleClass="text"/>
			   		</td> 		   		
			   		<td align="right">
			   			<html:button property="btn" accesskey="a" styleClass="addtocart bigbutton" onclick="addToCart()">
							&nbsp;
						</html:button>					   			
			   		</td>		   			   				
				</tr>
				<tr>
	   				<td>
	   					<label><pos:element columnName="search.product"/>:</label>
	   				</td>	   				
	   				<td colspan="3">
						<%@ include file="/jsp/include/searchProductPanel.jsp" %>
					</td><td>&nbsp;</td>
	   			</tr>	
   			</table>
   		</td>
   	</tr>

	<tr>
		<td>
			<div id="shoppingCart">
				<%@ include file="/jsp/pos/customerReturnShoppingCart.jsp" %>
			</div>
		</td>
	</tr> 
	<tr>
		<td valign="top">
			<table class="main">
			<tr>
			<td width="45%">
				<fieldset>
				<legend><pos:element columnName="reason.message"/></legend>
				<div style="height:150px;width:300px">
				<label><pos:element columnName="reason.message"/></label><br>
								
				<logic:present name="<%= Constants.DESCRIPTION %>">
				<bean:define id="description">
					<bean:write name="<%= Constants.DESCRIPTION %>"/>
				</bean:define>
				<html:textarea property="description" onchange="assignDescription()" styleClass="text" accesskey="r" value="<%= description %>"/>
				</logic:present>
				
				<logic:notPresent name="<%= Constants.DESCRIPTION %>">
				<html:textarea property="description" onchange="assignDescription()" styleClass="text" accesskey="r"/>
				</logic:notPresent>
									
				</div>
				</fieldset>
			</td>
			<td width="45%">
				<%@ include file="/jsp/include/customerInfoPanel.jsp" %>	
			</td>
			<td align="right" valign="bottom">
				<html:button property="btn" accesskey="c" styleClass="continue smallbutton" onclick="createOrder()">&nbsp;</html:button>
			</td>
			</tr>
			</table>
		</td>
	</tr> 	
				
	<tr>					  
		<td align="center">				
			<span>
			<span class="shortcutkey">
				<label><pos:element columnName="reason.message"/>-</label>
				<label class="red">Alt-R</label>
			</span>	
			<span class="shortcutkey">
				<label><pos:element columnName="barcode"/>-</label>
				<label class="red">Alt-B</label>
			</span>	
			<span class="shortcutkey">
				<label><pos:element columnName="Qty"/>-</label>
				<label class="red">Alt-Q</label>
			</span>
			<span class="shortcutkey">
				<label><pos:element columnName="add.to.cart"/>-</label>
				<label class="red">Alt-A</label>
			</span>
			<span class="shortcutkey">
				<label><pos:element columnName="continue"/>-</label>-</label>
				<label class="red">Alt-C</label>
			</span>
			<span class="shortcutkey">
				<label><pos:element columnName="search.product"/>-</label>
				<label class="red">Alt-P</label>
			</span>
			<span class="shortcutkey">
				<label><pos:element columnName="search.customer"/>-</label>
				<label class="red">Alt-U</label>
			</span>						
			</span>	
		</td>  		
	</tr>
</table>
		
</html:form>
<script>	
	document.getElementsByName('barCode')[0].value="";
	document.getElementsByName('quantity')[0].value="1";
	document.getElementsByName('barCode')[0].focus();	
		
	
	
	
	
	function addToCart()
	{
		try
		{
			var method = "addToShoppingCart";
			var myForm = document.forms[0];
			var actionElement = document.getElementsByName('action')[0];
			
			var productIdElement = document.getElementsByName('productId')[0];
			var ifAddElement =	document.getElementsByName('ifAdd')[0];	
			
			actionElement.value = method;
			//productIdElement = "";
			ifAddElement = "true";
			
			myForm.submit();
		}
		catch(e)
		{
			toConsole(e);
		}	
	}
	
	function createOrder()
	{
		var method = "createCustomerReturnOrder";
		var myForm = document.forms[0];
		var actionElement = document.getElementsByName('action')[0];
		
		actionElement.value = method;
		myForm.submit();
	}
	
	
	
	function setQuantity()
	{
		document.getElementsByName('quantity')[0].value=prompt("Enter Quantity");
		
	}
	
	function assignDescription()
	{
		var desc=document.getElementsByName('description').value;
		document.getElementsByName('descript').value=desc;
	}
	
	function focusBarcode()
	{
		document.getElementsByName('barCode')[0].focus();
	}
	
	
	$focus('barCode');
	
	document.forms[0].barCode.onkeyup = function(e){
	
		try
		{
			var event = e||window.event;
		
			if(event.keyCode == 13)
			{
				addToCart();
			}
		}
		catch (e)
		{
			toConsole(e);
		}
	};
	
	//incrementing and decrementing the shopping cart items using ajax
	var orderType = $FElement('orderType').value;	
	function incrementCart(productId)
	{
		try
		{
			var url = 'CreateGoodsReceiveAction.do';
			var pars = 'action=incrementQty&productId='+productId+ '&ifAdd=true'+ '&isSales=true'+ '&orderType=' + orderType;
			
			var myAjax = new Ajax.Request( url, 
			{ 
				method: 'get', 
				parameters: pars, 
				onSuccess: refreshShoppingCart, 
				onFailure: reportError
			});	
			
			
			
			//myForm.submit();
		}
		catch(e)
		{
			toConsole(e);
		}		
	}
	
	
	
	function refreshShoppingCart(request)
	{
		$('shoppingCart').innerHTML = request.responseText;
	}
	
	function decrementCart(productId)
	{
		try
		{
			var url = 'CreateGoodsReceiveAction.do';
			var pars = 'action=decrementQty&productId='+productId+ '&ifAdd=false'+ '&isSales=true'+ '&orderType=' + orderType;
			
			var myAjax = new Ajax.Request( url, 
			{ 
				method: 'get', 
				parameters: pars, 
				onSuccess: refreshShoppingCart, 
				onFailure: reportError
			});	
			//myForm.submit();
		}
		catch(e)
		{
			toConsole(e);
		}		
	}
	
	function deleteItemFromCart(productId)
	{
		try
		{
			var url = 'DeleteFromShoppingCartAction.do';
			var pars = 'action=deleteFromPOSGRNCart&productId='+productId+ '&orderType=' + orderType;
			
			var myAjax = new Ajax.Request( url, 
			{ 
				method: 'get', 
				parameters: pars, 
				onSuccess: refreshShoppingCart, 
				onFailure: reportError
			});
		}
		catch(e)
		{
			toConsole(e);
		}
	}
	
	function reportError(request)
	{
		alert('<pos:element textOnly="true" columnName="ajax.error"/>');		
	}
	
	function scrollDownCart()
	{
		$('items').scrollTop = $('items').scrollHeight;
	}
	
	try
	{		
		addRequiredLibrary('js/customer.js');		
	}
	catch(e)
	{
		alert(e);
	}
	
	document.getElementsByName('barCode')[0].value="";
	document.getElementsByName('quantity')[0].value="1";
	
	focusBarcode();
	//scrollDownCart();
	
</script>    
 
<%@ include file="/jsp/include/posFooter.jsp" %>      
   

$(":button").click(function() {
	//var isbn = $("#UID").text();
	var isbn=$(this).closest("tr").find("td").eq(0).text();
	jQuery.ajax({
	    type: "PUT",
	    url: "http://localhost:8001/library/v1/books/"+isbn+"?status=lost",
	    contentType: "application/json",
	    success : function ()
	    {
	    	location.reload();
	    	 $(":button").attr("disabled",true);
			alert('Success.');
	    },
		failure : function ()
		{
			alert('failure...');
		}
	});
    });
   
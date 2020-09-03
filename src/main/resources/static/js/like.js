function like(){
	$("#image-1").toggleClass("heart-clicked fa-heart fa-heart-o");
}

async function like(imageId) {
	 let response = await fetch("/likes/" + imageId,{
		  method : "post" 
	 });
	 
	 let result = await response.text(); 
	 if(result === "ok"){
		  location.reload();
	 }
}

async function unlike(imageId) {
    let response = await fetch("/likes/" + imageId,{
    	
    	method:  "delete"
    });	
    let result = await response.text(); 
    if(result === "ok"){
    	location.reload();
    }
}

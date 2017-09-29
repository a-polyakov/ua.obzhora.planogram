var images=[];

function getImage(code_image)
{
	if(images[code_image] === undefined)
	{
//			var loadComplite=0;
		images[code_image]= new Image();
//			images[code_image].onload  =function (){
//				loadComplite=1;
//			}
		images[code_image].src = 'image/'+code_image;
		// TODO загрузить, а лиш потом вернуть
//			images[code_image].load('image/'+code_image);
//			while (loadComplite==0);
		return images[code_image];
	}else
	{
		return images[code_image];
	}
}

function rackWaresLoadImage()
{
	var countImg=window.rackWaresList.length;
	for (var i = 0; i < window.rackWaresList.length; i++) {
		var code_image=window.rackWaresList[i].code_image;
		if(code_image>0)
		{
			images[code_image] = new Image();
			images[code_image].onload = function(){
				countImg--;
				if (countImg==0)
				{
					drawEditCanvas();
					drawPreviewCanvas();
				}
			}
			images[code_image].src = 'image/'+code_image;
		}
	}
	drawEditCanvas();
	drawPreviewCanvas();
}
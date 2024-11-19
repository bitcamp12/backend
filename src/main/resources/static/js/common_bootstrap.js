window.onload = function() {

	// Bootstrap CSS 추가
	var bootstrapCSS = document.createElement('link');
	bootstrapCSS.rel = 'stylesheet';
	bootstrapCSS.href = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css';
	document.head.appendChild(bootstrapCSS);
	
	// Bootstrap Refine CSS 추가
	var refineCSS = document.createElement('link');
	refineCSS.rel = 'stylesheet';
	refineCSS.href = '../../static/css/bootstrap_refine.css';
	document.head.appendChild(refineCSS);
	
	// Bootstrap JS 추가
	var bootstrapJS = document.createElement('script');
	bootstrapJS.src = 'https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js';
	document.body.appendChild(bootstrapJS);

};
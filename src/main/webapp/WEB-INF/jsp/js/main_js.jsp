<%@ page language="java" contentType="application/javascript; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

// jsp
var playerNames = ["Joe", "Matt", "Bob"];

var drawSvg = function(svgImage) {'<svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg">'+svgImage+'</svg>'};
var svgDetail[] = [ function(x,y) { '<rect x="'+x+'" y="'+y+'" height="100" width="100" /> rx="15"' } ];
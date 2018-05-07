function toggleElement(elementId, displayStyle)
{
    var current = getStyle(elementId, 'display');
    document.getElementById(elementId).style.display = (current == 'none' ? displayStyle : 'none');
}


function getStyle(elementId, property)
{
    var element = document.getElementById(elementId);
    return element.currentStyle ? element.currentStyle[property]
           : document.defaultView.getComputedStyle(element, null).getPropertyValue(property);
}


function toggle(toggleId)
{
    var toggle;
    if (document.getElementById)
    {
        toggle = document.getElementById(toggleId);
    }
    else if (document.all)
    {
        toggle = document.all[toggleId];
    }
    toggle.textContent = toggle.innerHTML == '\u25b6' ? '\u25bc' : '\u25b6';
}

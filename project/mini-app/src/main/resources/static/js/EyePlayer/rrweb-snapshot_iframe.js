var NodeType;
(function (NodeType) {
    NodeType[NodeType["Document"] = 0] = "Document";
    NodeType[NodeType["DocumentType"] = 1] = "DocumentType";
    NodeType[NodeType["Element"] = 2] = "Element";
    NodeType[NodeType["Text"] = 3] = "Text";
    NodeType[NodeType["CDATA"] = 4] = "CDATA";
    NodeType[NodeType["Comment"] = 5] = "Comment";
})(NodeType || (NodeType = {}));

var _id = 1;
function genId() {
    return _id++;
}
function resetId() {
    _id = 1;
}
function getCssRulesString(s) {
    try {
        var rules = s.rules || s.cssRules;
        return rules
            ? Array.from(rules).reduce(function (prev, cur) { return (prev += cur.cssText); }, '')
            : null;
    }
    catch (error) {
        return null;
    }
}
function extractOrigin(url) {
    var origin;
    if (url.indexOf('//') > -1) {
        origin = url
            .split('/')
            .slice(0, 3)
            .join('/');
    }
    else {
        origin = url.split('/')[0];
    }
    origin = origin.split('?')[0];
    return origin;
}
var URL_IN_CSS_REF = /url\((?:'([^']*)'|"([^"]*)"|([^)]*))\)/gm;
var RELATIVE_PATH = /^(?!www\.|(?:http|ftp)s?:\/\/|[A-Za-z]:\\|\/\/).*/;
var DATA_URI = /^(data:)([\w\/\+]+);(charset=[\w-]+|base64).*,(.*)/gi;
function absoluteToStylesheet(cssText, href) {
    return (cssText || '').replace(URL_IN_CSS_REF, function (origin, path1, path2, path3) {
        var filePath = path1 || path2 || path3;
        if (!filePath) {
            return origin;
        }

        if (!RELATIVE_PATH.test(filePath)) {
            window.URLList.push(filePath);
            return "url('" + filePath + "')";
        }
        if (DATA_URI.test(filePath)) {

            return "url(" + filePath + ")";
        }
        if (filePath[0] === '/') {
            window.URLList.push(extractOrigin(href) + filePath);
            return "url('" + (extractOrigin(href) + filePath) + "')";
        }
        var stack = href.split('/');
        var parts = filePath.split('/');
        stack.pop();
        for (var _i = 0, parts_1 = parts; _i < parts_1.length; _i++) {
            var part = parts_1[_i];
            if (part === '.') {
                continue;
            }
            else if (part === '..') {
                stack.pop();
            }
            else {
                stack.push(part);
            }
        }
        window.URLList.push(stack.join('/'));
        return "url('" + stack.join('/') + "')";
    });
}
function getAbsoluteSrcsetString(doc, attributeValue) {
    if (attributeValue.trim() === '') {
        return attributeValue;
    }
    var srcsetValues = attributeValue.split(',');
    var resultingSrcsetString = srcsetValues
        .map(function (srcItem) {
            var trimmedSrcItem = srcItem.trimLeft().trimRight();
            var urlAndSize = trimmedSrcItem.split(' ');
            if (urlAndSize.length === 2) {
                var absUrl = absoluteToDocSrc(doc, urlAndSize[0]);
                return absUrl + " " + urlAndSize[1];
            }
            else if (urlAndSize.length === 1) {
                var absUrl = absoluteToDocSrc(doc, urlAndSize[0]);
                return "" + absUrl;
            }
            return '';
        })
        .join(',');
    return resultingSrcsetString;
}
function absoluteToDocSrc(doc, attributeValue) {
    if (!attributeValue || attributeValue.trim() === '') {
        return attributeValue;
    }
    var a = doc.createElement('a');
    a.href = attributeValue;
    //图片信息保存；
    window.URLList.push(a.href);
    return a.href;
}
function absoluteToDoc(doc, attributeValue) {
    var a = doc.createElement('a');
    a.href = attributeValue;
    return a.href;
}
function isSVGElement(el) {
    return el.tagName === 'svg' || el instanceof SVGElement;
}
function transformAttribute(doc, name, value, tagName) {
    if (name === 'href' && value) {
        return absoluteToDoc(doc, value);
    } else if (name === 'src' && tagName === 'script') {
        if(value.indexOf('/webeye/sdk/webrecord.js')!==-1) {

            return "";
        }
        return absoluteToDoc(doc, value);

    } else if (name === 'src' && tagName !== 'script') {

        return absoluteToDocSrc(doc, value);
    }
    else if (name === 'srcset' && value) {
        return getAbsoluteSrcsetString(doc, value);
    }
    else if (name === 'style' && value) {
        return absoluteToStylesheet(value, location.href);
    }
    else {
        return value;
    }
}
function serializeNode(n, doc, blockClass) {
    var rootId;
    var maskInputOptions = {}; //要隐藏的输入数据
    if (doc.__sn) {
        var docId = doc.__sn.id;
        rootId = docId === 1 ? undefined : docId;
    }
    switch (n.nodeType) {
        case n.DOCUMENT_NODE:
            return {
                type: NodeType.Document,
                childNodes: [],
                rootId: rootId,
            };
        case n.DOCUMENT_TYPE_NODE:
            return {
                type: NodeType.DocumentType,
                name: n.name,
                publicId: n.publicId,
                systemId: n.systemId,
                rootId: rootId,
            };
        case n.ELEMENT_NODE:
            var needBlock_1 = false;
            if (typeof blockClass === 'string') {
                needBlock_1 = n.classList.contains(blockClass);
            }
            else {
                n.classList.forEach(function (className) {
                    if (blockClass.test(className)) {
                        needBlock_1 = true;
                    }
                });
            }
            if(n.id =="__vconsole") {
                needBlock_1 = true;//控制id为这个的不监听
              }
            var tagName = n.tagName.toLowerCase();
            var attributes_1 = {};
            for (var _i = 0, _a = Array.from(n.attributes); _i < _a.length; _i++) {
                var _b = _a[_i], name = _b.name, value = _b.value;
                attributes_1[name] = transformAttribute(doc, name, value, tagName);
            }
            if (tagName === 'link') {
                var stylesheet = Array.from(doc.styleSheets).find(function (s) {
                    return s.href === n.href;
                });
                var cssText = getCssRulesString(stylesheet);
                if (cssText) {
                    delete attributes_1.rel;
                    delete attributes_1.href;
                    attributes_1._cssText = absoluteToStylesheet(cssText, stylesheet.href);
                } else {
                    
                    window.URLList.push(attributes_1.href);
                }
            }
            if (tagName === 'style' &&
                n.sheet &&
                !n.innerText.trim().length) {
                var cssText = getCssRulesString(n
                    .sheet);
                if (cssText) {
                    attributes_1._cssText = absoluteToStylesheet(cssText, location.href);
                }
            }
            if (tagName === 'input' ||
                tagName === 'textarea' ||
                tagName === 'select') {
                var value = n.value;
                if (attributes_1.type !== 'radio' &&
                    attributes_1.type !== 'checkbox' &&
                    attributes_1.type !== 'submit' &&
                    attributes_1.type !== 'button' &&
                    value) {
                    attributes_1.value =
                        maskInputOptions[attributes_1.type] ||
                            maskInputOptions[tagName]
                            ? '*'.repeat(value.length)
                            : value;
                }
                else if (n.checked) {
                    attributes_1.checked = n.checked;
                }
            }
            if (tagName === 'option') {
                var selectValue = n.parentElement;
                if (attributes_1.value === selectValue.value) {
                    attributes_1.selected = n.selected;
                }
            }
            if (needBlock_1) {
                var _c = n.getBoundingClientRect(), width = _c.width, height = _c.height;
                attributes_1.rr_width = width + "px";
                attributes_1.rr_height = height + "px";
            }
            return {
                type: NodeType.Element,
                tagName: tagName,
                attributes: attributes_1,
                childNodes: [],
                isSVG: isSVGElement(n) || undefined,
                needBlock: needBlock_1,
                rootId: rootId,
            };
        case n.TEXT_NODE:
            var parentTagName = n.parentNode && n.parentNode.tagName;
            var textContent = n.textContent;
            var isStyle = parentTagName === 'STYLE' ? true : undefined;
            if (isStyle && textContent) {
                textContent = absoluteToStylesheet(textContent, location.href);
            }
            if (parentTagName === 'SCRIPT') {
                textContent = 'SCRIPT_PLACEHOLDER';
            }
            return {
                type: NodeType.Text,
                textContent: textContent || '',
                isStyle: isStyle,
                rootId: rootId,
            };
        case n.CDATA_SECTION_NODE:
            return {
                type: NodeType.CDATA,
                textContent: '',
                rootId: rootId,
            };
        case n.COMMENT_NODE:
            return {
                type: NodeType.Comment,
                textContent: n.textContent || '',
                rootId: rootId,
            };
        default:
            return false;
    }
}
function serializeNodeWithId(n, doc, map, options) {
    if (options === void 0) { options = {}; }
    var _a = options.blockClass, blockClass = _a === void 0 ? 'webeye-ignore-block' : _a, _b = options.skipChild, skipChild = _b === void 0 ? false : _b, onVisit = options.onVisit;
    var _serializedNode = serializeNode(n, doc, blockClass);
    if (!_serializedNode) {
        console.warn(n, 'not serialized');
        return null;
    }
    var serializedNode = Object.assign(_serializedNode, {
        id: genId(),
    });
    n.__sn = serializedNode;
    map[serializedNode.id] = n;
    if (onVisit) {
        onVisit(n);
    }
    var recordChild = !skipChild;
    if (serializedNode.type === NodeType.Element) {
        recordChild = recordChild && !serializedNode.needBlock;
        delete serializedNode.needBlock;
    }
    if ((serializedNode.type === NodeType.Document ||
        serializedNode.type === NodeType.Element) &&
        recordChild) {
        for (var _i = 0, _c = Array.from(n.childNodes); _i < _c.length; _i++) {
            var childN = _c[_i];
            var serializedChildNode = serializeNodeWithId(childN, doc, map, options);
            if (serializedChildNode) {
                serializedNode.childNodes.push(serializedChildNode);
            }
        }
    }
    if (serializedNode.type === NodeType.Element &&
        serializedNode.tagName === 'iframe') {
        var iframeDoc = n.contentDocument;
        if (iframeDoc) {
            var serializedIframeNode = serializeNodeWithId(iframeDoc, iframeDoc, map, options);
            if (serializedIframeNode) {
                serializedNode.childNodes.push(serializedIframeNode);
            }
        }
    }
    return serializedNode;
}
function snapshot(n, options) {
    resetId();
    var idNodeMap = {};
    return [serializeNodeWithId(n, n, idNodeMap, options), idNodeMap];
}

var tagMap = {
    script: 'noscript',
    altglyph: 'altGlyph',
    altglyphdef: 'altGlyphDef',
    altglyphitem: 'altGlyphItem',
    animatecolor: 'animateColor',
    animatemotion: 'animateMotion',
    animatetransform: 'animateTransform',
    clippath: 'clipPath',
    feblend: 'feBlend',
    fecolormatrix: 'feColorMatrix',
    fecomponenttransfer: 'feComponentTransfer',
    fecomposite: 'feComposite',
    feconvolvematrix: 'feConvolveMatrix',
    fediffuselighting: 'feDiffuseLighting',
    fedisplacementmap: 'feDisplacementMap',
    fedistantlight: 'feDistantLight',
    fedropshadow: 'feDropShadow',
    feflood: 'feFlood',
    fefunca: 'feFuncA',
    fefuncb: 'feFuncB',
    fefuncg: 'feFuncG',
    fefuncr: 'feFuncR',
    fegaussianblur: 'feGaussianBlur',
    feimage: 'feImage',
    femerge: 'feMerge',
    femergenode: 'feMergeNode',
    femorphology: 'feMorphology',
    feoffset: 'feOffset',
    fepointlight: 'fePointLight',
    fespecularlighting: 'feSpecularLighting',
    fespotlight: 'feSpotLight',
    fetile: 'feTile',
    feturbulence: 'feTurbulence',
    foreignobject: 'foreignObject',
    glyphref: 'glyphRef',
    lineargradient: 'linearGradient',
    radialgradient: 'radialGradient',
};
function getTagName(n) {
    var tagName = tagMap[n.tagName] ? tagMap[n.tagName] : n.tagName;
    if (tagName === 'link' && n.attributes._cssText) {
        tagName = 'style';
    }
    return tagName;
}
var CSS_SELECTOR = /([^\r\n,{}]+)(,(?=[^}]*{)|\s*{)/g;
var HOVER_SELECTOR = /([^\\]):hover/g;
function addHoverClass(cssText) {
    return cssText.replace(CSS_SELECTOR, function (match, p1, p2) {
        if (HOVER_SELECTOR.test(p1)) {
            var newSelector = p1.replace(HOVER_SELECTOR, '$1.\\:hover');
            return p1.replace(/\s*$/, '') + ", " + newSelector.replace(/^\s*/, '') + p2;
        }
        else {
            return match;
        }
    });
}
function buildNode(n, doc) {
    switch (n.type) {
        case NodeType.Document:
            return doc.implementation.createDocument(null, '', null);
        case NodeType.DocumentType:
            return doc.implementation.createDocumentType(n.name, n.publicId, n.systemId);
        case NodeType.Element:
            var tagName = getTagName(n);
            var node = void 0;
            if (n.isSVG) {
                node = doc.createElementNS('http://www.w3.org/2000/svg', tagName);
            }
            else {
                node = doc.createElement(tagName);
            }
            for (var name in n.attributes) {
                if (n.attributes.hasOwnProperty(name) && !name.startsWith('rr_')) {
                    var value = n.attributes[name];
                    value = typeof value === 'boolean' ? '' : value;
                    var isTextarea = tagName === 'textarea' && name === 'value';
                    var isRemoteOrDynamicCss = tagName === 'style' && name === '_cssText';
                    if (isRemoteOrDynamicCss) {
                        value = addHoverClass(value);
                    }
                    if (isTextarea || isRemoteOrDynamicCss) {
                        var child = doc.createTextNode(value);
                        node.appendChild(child);
                        continue;
                    }
                    if (tagName === 'iframe' && name === 'src') {
                        continue;
                    }
                    try {
                        node.setAttribute(name, value);
                    }
                    catch (error) {
                    }
                }
                else {
                    if (n.attributes.rr_width) {
                        node.style.width = n.attributes.rr_width;
                    }
                    if (n.attributes.rr_height) {
                        node.style.height = n.attributes
                            .rr_height;
                    }
                }
            }
            return node;
        case NodeType.Text:
            return doc.createTextNode(n.isStyle ? addHoverClass(n.textContent) : n.textContent);
        case NodeType.CDATA:
            return doc.createCDATASection(n.textContent);
        case NodeType.Comment:
            return doc.createComment(n.textContent);
        default:
            return null;
    }
}
function isIframe(n) {
    return n.type === NodeType.Element && n.tagName === 'iframe';
}
function buildIframe(iframe, childNodes, map, cbs) {
    var targetDoc = iframe.contentDocument;
    for (var _i = 0, childNodes_1 = childNodes; _i < childNodes_1.length; _i++) {
        var childN = childNodes_1[_i];
        buildNodeWithSN(childN, targetDoc, map, cbs);
    }
}
function buildNodeWithSN(n, doc, map, cbs, skipChild) {
    if (skipChild === void 0) { skipChild = false; }
    var node = buildNode(n, doc);
    if (!node) {
        return [null, []];
    }
    if (n.rootId) {
        console.assert(map[n.rootId] === doc, 'Target document should has the same root id.');
    }
    if (n.type === NodeType.Document) {
        doc.close();
        doc.open();
        node = doc;
    }
    node.__sn = n;
    map[n.id] = node;
    if ((n.type === NodeType.Document || n.type === NodeType.Element) &&
        !skipChild) {
        var nodeIsIframe = isIframe(n);
        if (nodeIsIframe) {
            return [node, n.childNodes];
        }
        var _loop_1 = function (childN) {
            var _a = buildNodeWithSN(childN, doc, map, cbs), childNode = _a[0], nestedNodes = _a[1];
            if (!childNode) {
                console.warn('Failed to rebuild', childN);
                return "continue";
            }
            node.appendChild(childNode);
            if (nestedNodes.length === 0) {
                return "continue";
            }
            var childNodeIsIframe = isIframe(childN);
            if (childNodeIsIframe) {
                cbs.push(function () {
                    return buildIframe(childNode, nestedNodes, map, cbs);
                });
            }
        };
        for (var _i = 0, _a = n.childNodes; _i < _a.length; _i++) {
            var childN = _a[_i];
            _loop_1(childN);
        }
    }
    return [node, []];
}
function rebuild(n, doc) {
    var idNodeMap = {};
    var callbackArray = [];
    var node = buildNodeWithSN(n, doc, idNodeMap, callbackArray)[0];
    callbackArray.forEach(function (f) { return f(); });
    return [node, idNodeMap];
}

export { snapshot, serializeNodeWithId, resetId, rebuild, buildNodeWithSN, NodeType,transformAttribute };

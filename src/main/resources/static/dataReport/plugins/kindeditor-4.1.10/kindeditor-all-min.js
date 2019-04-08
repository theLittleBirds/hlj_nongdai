/* KindEditor 4.1.10 (2013-11-23), Copyright (C) kindsoft.net, Licence: http://www.kindsoft.net/license.php */
(function (b, d) {
    function f(a) {
        if (!a)return !1;
        return Object.prototype.toString.call(a) === "[object Array]"
    }

    function j(a) {
        if (!a)return !1;
        return Object.prototype.toString.call(a) === "[object Function]"
    }

    function e(a, c) {
        for (var g = 0, b = c.length; g < b; g++)if (a === c[g])return g;
        return -1
    }

    function h(a, c) {
        if (f(a))for (var g = 0, b = a.length; g < b; g++) {
            if (c.call(a[g], g, a[g]) === !1)break
        } else for (g in a)if (a.hasOwnProperty(g) && c.call(a[g], g, a[g]) === !1)break
    }

    function m(a) {
        return a.replace(/(?:^[ \t\n\r]+)|(?:[ \t\n\r]+$)/g, "")
    }

    function n(a, c, g) {
        g = g === d ? "," : g;
        return (g + c + g).indexOf(g + a + g) >= 0
    }

    function o(a, c) {
        c = c || "px";
        return a && /^\d+$/.test(a) ? a + c : a
    }

    function l(a) {
        var c;
        return a && (c = /(\d+)/.exec(a)) ? parseInt(c[1], 10) : 0
    }

    function s(a) {
        return a.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/"/g, "&quot;")
    }

    function v(a) {
        return a.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&quot;/g, '"').replace(/&amp;/g, "&")
    }

    function p(a) {
        var c = a.split("-"), a = "";
        h(c, function (c, b) {
            a += c > 0 ? b.charAt(0).toUpperCase() +
            b.substr(1) : b
        });
        return a
    }

    function r(a) {
        function c(a) {
            a = parseInt(a, 10).toString(16).toUpperCase();
            return a.length > 1 ? a : "0" + a
        }

        return a.replace(/rgb\s*\(\s*(\d+)\s*,\s*(\d+)\s*,\s*(\d+)\s*\)/ig, function (a, b, d, k) {
            return "#" + c(b) + c(d) + c(k)
        })
    }

    function z(a, c) {
        var c = c === d ? "," : c, g = {}, b = f(a) ? a : a.split(c), t;
        h(b, function (a, c) {
            if (t = /^(\d+)\.\.(\d+)$/.exec(c))for (var b = parseInt(t[1], 10); b <= parseInt(t[2], 10); b++)g[b.toString()] = !0; else g[c] = !0
        });
        return g
    }

    function D(a, c) {
        return Array.prototype.slice.call(a, c || 0)
    }

    function q(a,
               c) {
        return a === d ? c : a
    }

    function A(a, c, g) {
        g || (g = c, c = null);
        var b;
        if (c) {
            var d = function () {
            };
            d.prototype = c.prototype;
            b = new d;
            h(g, function (a, c) {
                b[a] = c
            })
        } else b = g;
        b.constructor = a;
        a.prototype = b;
        a.parent = c ? c.prototype : null
    }

    function B(a) {
        var c;
        if (c = /\{[\s\S]*\}|\[[\s\S]*\]/.exec(a))a = c[0];
        c = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g;
        c.lastIndex = 0;
        c.test(a) && (a = a.replace(c, function (a) {
            return "\\u" + ("0000" + a.charCodeAt(0).toString(16)).slice(-4)
        }));
        if (/^[\],:{}\s]*$/.test(a.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, "@").replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, "]").replace(/(?:^|:|,)(?:\s*\[)+/g, "")))return eval("(" + a + ")");
        throw"JSON parse error";
    }

    function G(a, c, g) {
        a.addEventListener ? a.addEventListener(c, g, fb) : a.attachEvent && a.attachEvent("on" + c, g)
    }

    function C(a, c, g) {
        a.removeEventListener ? a.removeEventListener(c, g, fb) : a.detachEvent && a.detachEvent("on" + c, g)
    }

    function u(a, c) {
        this.init(a, c)
    }

    function I(a) {
        try {
            delete a[ma]
        } catch (c) {
            a.removeAttribute &&
            a.removeAttribute(ma)
        }
    }

    function E(a, c, g) {
        if (c.indexOf(",") >= 0)h(c.split(","), function () {
            E(a, this, g)
        }); else {
            var b = a[ma] || null;
            b || (a[ma] = ++gb, b = gb);
            L[b] === d && (L[b] = {});
            var t = L[b][c];
            t && t.length > 0 ? C(a, c, t[0]) : (L[b][c] = [], L[b].el = a);
            t = L[b][c];
            t.length === 0 && (t[0] = function (c) {
                var g = c ? new u(a, c) : d;
                h(t, function (c, b) {
                    c > 0 && b && b.call(a, g)
                })
            });
            e(g, t) < 0 && t.push(g);
            G(a, c, t[0])
        }
    }

    function T(a, c, g) {
        if (c && c.indexOf(",") >= 0)h(c.split(","), function () {
            T(a, this, g)
        }); else {
            var b = a[ma] || null;
            if (b)if (c === d)b in L && (h(L[b],
                function (c, g) {
                    c != "el" && g.length > 0 && C(a, c, g[0])
                }), delete L[b], I(a)); else if (L[b]) {
                var t = L[b][c];
                if (t && t.length > 0) {
                    g === d ? (C(a, c, t[0]), delete L[b][c]) : (h(t, function (a, c) {
                        a > 0 && c === g && t.splice(a, 1)
                    }), t.length == 1 && (C(a, c, t[0]), delete L[b][c]));
                    var k = 0;
                    h(L[b], function () {
                        k++
                    });
                    k < 2 && (delete L[b], I(a))
                }
            }
        }
    }

    function qa(a, c) {
        if (c.indexOf(",") >= 0)h(c.split(","), function () {
            qa(a, this)
        }); else {
            var g = a[ma] || null;
            if (g) {
                var b = L[g][c];
                if (L[g] && b && b.length > 0)b[0]()
            }
        }
    }

    function $(a, c, g) {
        c = /^\d{2,}$/.test(c) ? c : c.toUpperCase().charCodeAt(0);
        E(a, "keydown", function (b) {
            b.ctrlKey && b.which == c && !b.shiftKey && !b.altKey && (g.call(a), b.stop())
        })
    }

    function M(a) {
        for (var c = {}, g = /\s*([\w\-]+)\s*:([^;]*)(;|$)/g, b; b = g.exec(a);) {
            var d = m(b[1].toLowerCase());
            b = m(r(b[2]));
            c[d] = b
        }
        return c
    }

    function K(a) {
        for (var c = {}, g = /\s+(?:([\w\-:]+)|(?:([\w\-:]+)=([^\s"'<>]+))|(?:([\w\-:"]+)="([^"]*)")|(?:([\w\-:"]+)='([^']*)'))(?=(?:\s|\/|>)+)/g, b; b = g.exec(a);) {
            var d = (b[1] || b[2] || b[4] || b[6]).toLowerCase();
            c[d] = (b[2] ? b[3] : b[4] ? b[5] : b[7]) || ""
        }
        return c
    }

    function O(a, c) {
        return a =
            /\s+class\s*=/.test(a) ? a.replace(/(\s+class=["']?)([^"']*)(["']?[\s>])/, function (a, b, d, k) {
                return (" " + d + " ").indexOf(" " + c + " ") < 0 ? d === "" ? b + c + k : b + d + " " + c + k : a
            }) : a.substr(0, a.length - 1) + ' class="' + c + '">'
    }

    function Q(a) {
        var c = "";
        h(M(a), function (a, b) {
            c += a + ":" + b + ";"
        });
        return c
    }

    function R(a, c, g, b) {
        function t(a) {
            for (var a = a.split("/"), c = [], g = 0, b = a.length; g < b; g++) {
                var d = a[g];
                d == ".." ? c.length > 0 && c.pop() : d !== "" && d != "." && c.push(d)
            }
            return "/" + c.join("/")
        }

        function k(c, g) {
            if (a.substr(0, c.length) === c) {
                for (var d = [], t =
                    0; t < g; t++)d.push("..");
                t = ".";
                d.length > 0 && (t += "/" + d.join("/"));
                b == "/" && (t += "/");
                return t + a.substr(c.length)
            } else if (i = /^(.*)\//.exec(c))return k(i[1], ++g)
        }

        c = q(c, "").toLowerCase();
        a.substr(0, 5) != "data:" && (a = a.replace(/([^:])\/\//g, "$1/"));
        if (e(c, ["absolute", "relative", "domain"]) < 0)return a;
        g = g || location.protocol + "//" + location.host;
        if (b === d)var w = location.pathname.match(/^(\/.*)\//), b = w ? w[1] : "";
        var i;
        if (i = /^(\w+:\/\/[^\/]*)/.exec(a)) {
            if (i[1] !== g)return a
        } else if (/^\w+:/.test(a))return a;
        /^\//.test(a) ?
            a = g + t(a.substr(1)) : /^\w+:\/\//.test(a) || (a = g + t(b + "/" + a));
        c === "relative" ? a = k(g + b, 0).substr(2) : c === "absolute" && a.substr(0, g.length) === g && (a = a.substr(g.length));
        return a
    }

    function H(a, c, g, b, d) {
        a == null && (a = "");
        var g = g || "", b = q(b, !1), d = q(d, "\t"), k = "xx-small,x-small,small,medium,large,x-large,xx-large".split(","), a = a.replace(/(<(?:pre|pre\s[^>]*)>)([\s\S]*?)(<\/pre>)/ig, function (a, c, g, b) {
            return c + g.replace(/<(?:br|br\s[^>]*)>/ig, "\n") + b
        }), a = a.replace(/<(?:br|br\s[^>]*)\s*\/?>\s*<\/p>/ig, "</p>"), a = a.replace(/(<(?:p|p\s[^>]*)>)\s*(<\/p>)/ig,
            "$1<br />$2"), a = a.replace(/\u200B/g, ""), a = a.replace(/\u00A9/g, "&copy;"), a = a.replace(/\u00AE/g, "&reg;"), a = a.replace(/<[^>]+/g, function (a) {
            return a.replace(/\s+/g, " ")
        }), w = {};
        c && (h(c, function (a, c) {
            for (var g = a.split(","), b = 0, d = g.length; b < d; b++)w[g[b]] = z(c)
        }), w.script || (a = a.replace(/(<(?:script|script\s[^>]*)>)([\s\S]*?)(<\/script>)/ig, "")), w.style || (a = a.replace(/(<(?:style|style\s[^>]*)>)([\s\S]*?)(<\/style>)/ig, "")));
        var i = [], a = a.replace(/(\s*)<(\/)?([\w\-:]+)((?:\s+|(?:\s+[\w\-:]+)|(?:\s+[\w\-:]+=[^\s"'<>]+)|(?:\s+[\w\-:"]+="[^"]*")|(?:\s+[\w\-:"]+='[^']*'))*)(\/)?>(\s*)/g,
            function (a, f, n, l, o, j, s) {
                var f = f || "", n = n || "", m = l.toLowerCase(), r = o || "", l = j ? " " + j : "", s = s || "";
                if (c && !w[m])return "";
                l === "" && hb[m] && (l = " /");
                ib[m] && (f && (f = " "), s && (s = " "));
                Ma[m] && (n ? s = "\n" : f = "\n");
                b && m == "br" && (s = "\n");
                if (jb[m] && !Ma[m])if (b) {
                    n && i.length > 0 && i[i.length - 1] === m ? i.pop() : i.push(m);
                    s = f = "\n";
                    o = 0;
                    for (j = n ? i.length : i.length - 1; o < j; o++)f += d, n || (s += d);
                    l ? i.pop() : n || (s += d)
                } else f = s = "";
                if (r !== "") {
                    var p = K(a);
                    if (m === "font") {
                        var v = {}, q = "";
                        h(p, function (a, c) {
                            if (a === "color")v.color = c, delete p[a];
                            a === "size" &&
                            (v["font-size"] = k[parseInt(c, 10) - 1] || "", delete p[a]);
                            a === "face" && (v["font-family"] = c, delete p[a]);
                            a === "style" && (q = c)
                        });
                        q && !/;$/.test(q) && (q += ";");
                        h(v, function (a, c) {
                            c !== "" && (/\s/.test(c) && (c = "'" + c + "'"), q += a + ":" + c + ";")
                        });
                        p.style = q
                    }
                    h(p, function (a, b) {
                        Lb[a] && (p[a] = a);
                        e(a, ["src", "href"]) >= 0 && (p[a] = R(b, g));
                        (c && a !== "style" && !w[m]["*"] && !w[m][a] || m === "body" && a === "contenteditable" || /^kindeditor_\d+$/.test(a)) && delete p[a];
                        if (a === "style" && b !== "") {
                            var d = M(b);
                            h(d, function (a) {
                                c && !w[m].style && !w[m]["." + a] && delete d[a]
                            });
                            var V = "";
                            h(d, function (a, c) {
                                V += a + ":" + c + ";"
                            });
                            p.style = V
                        }
                    });
                    r = "";
                    h(p, function (a, c) {
                        a === "style" && c === "" || (c = c.replace(/"/g, "&quot;"), r += " " + a + '="' + c + '"')
                    })
                }
                m === "font" && (m = "span");
                return f + "<" + n + m + r + l + ">" + s
            }), a = a.replace(/(<(?:pre|pre\s[^>]*)>)([\s\S]*?)(<\/pre>)/ig, function (a, c, g, b) {
            return c + g.replace(/\n/g, '<span id="__kindeditor_pre_newline__">\n') + b
        }), a = a.replace(/\n\s*\n/g, "\n"), a = a.replace(/<span id="__kindeditor_pre_newline__">\n/g, "\n");
        return m(a)
    }

    function U(a, c) {
        a = a.replace(/<meta[\s\S]*?>/ig,
            "").replace(/<![\s\S]*?>/ig, "").replace(/<style[^>]*>[\s\S]*?<\/style>/ig, "").replace(/<script[^>]*>[\s\S]*?<\/script>/ig, "").replace(/<w:[^>]+>[\s\S]*?<\/w:[^>]+>/ig, "").replace(/<o:[^>]+>[\s\S]*?<\/o:[^>]+>/ig, "").replace(/<xml>[\s\S]*?<\/xml>/ig, "").replace(/<(?:table|td)[^>]*>/ig, function (a) {
                return a.replace(/border-bottom:([#\w\s]+)/ig, "border:$1")
            });
        return H(a, c)
    }

    function W(a) {
        if (/\.(rm|rmvb)(\?|$)/i.test(a))return "audio/x-pn-realaudio-plugin";
        if (/\.(swf|flv)(\?|$)/i.test(a))return "application/x-shockwave-flash";
        return "video/x-ms-asf-plugin"
    }

    function S(a) {
        return K(unescape(a))
    }

    function Na(a) {
        var c = "<embed ";
        h(a, function (a, b) {
            c += a + '="' + b + '" '
        });
        c += "/>";
        return c
    }

    function kb(a, c) {
        var g = c.width, b = c.height, d = c.type || W(c.src), k = Na(c), w = "";
        /\D/.test(g) ? w += "width:" + g + ";" : g > 0 && (w += "width:" + g + "px;");
        /\D/.test(b) ? w += "height:" + b + ";" : b > 0 && (w += "height:" + b + "px;");
        g = /realaudio/i.test(d) ? "ke-rm" : /flash/i.test(d) ? "ke-flash" : "ke-media";
        g = '<img class="' + g + '" src="' + a + '" ';
        w !== "" && (g += 'style="' + w + '" ');
        g += 'data-ke-tag="' + escape(k) +
            '" alt="" />';
        return g
    }

    function Da(a, c) {
        if (a.nodeType == 9 && c.nodeType != 9)return !0;
        for (; c = c.parentNode;)if (c == a)return !0;
        return !1
    }

    function Ea(a, c) {
        var c = c.toLowerCase(), g = null;
        if (!Mb && a.nodeName.toLowerCase() != "script") {
            var b = a.ownerDocument.createElement("div");
            b.appendChild(a.cloneNode(!1));
            b = K(v(b.innerHTML));
            c in b && (g = b[c])
        } else try {
            g = a.getAttribute(c, 2)
        } catch (d) {
            g = a.getAttribute(c, 1)
        }
        c === "style" && g !== null && (g = Q(g));
        return g
    }

    function Fa(a, c) {
        function g(a) {
            if (typeof a != "string")return a;
            return a.replace(/([^\w\-])/g,
                "\\$1")
        }

        function b(a, c) {
            return a === "*" || a.toLowerCase() === g(c.toLowerCase())
        }

        function d(a, c, g) {
            var t = [];
            (a = (g.ownerDocument || g).getElementById(a.replace(/\\/g, ""))) && b(c, a.nodeName) && Da(g, a) && t.push(a);
            return t
        }

        function k(a, c, g) {
            var d = g.ownerDocument || g, t = [], k, w, i;
            if (g.getElementsByClassName) {
                d = g.getElementsByClassName(a.replace(/\\/g, ""));
                k = 0;
                for (w = d.length; k < w; k++)i = d[k], b(c, i.nodeName) && t.push(i)
            } else if (d.querySelectorAll) {
                d = d.querySelectorAll((g.nodeName !== "#document" ? g.nodeName + " " : "") + c + "." +
                    a);
                k = 0;
                for (w = d.length; k < w; k++)i = d[k], Da(g, i) && t.push(i)
            } else {
                d = g.getElementsByTagName(c);
                a = " " + a + " ";
                k = 0;
                for (w = d.length; k < w; k++)if (i = d[k], i.nodeType == 1)(c = i.className) && (" " + c + " ").indexOf(a) > -1 && t.push(i)
            }
            return t
        }

        function w(a, c, b, d) {
            for (var t = [], b = d.getElementsByTagName(b), V = 0, k = b.length; V < k; V++)d = b[V], d.nodeType == 1 && (c === null ? Ea(d, a) !== null && t.push(d) : c === g(Ea(d, a)) && t.push(d));
            return t
        }

        function i(a, c) {
            var g = [], e, f = (e = /^((?:\\.|[^.#\s\[<>])+)/.exec(a)) ? e[1] : "*";
            if (e = /#((?:[\w\-]|\\.)+)$/.exec(a))g =
                d(e[1], f, c); else if (e = /\.((?:[\w\-]|\\.)+)$/.exec(a))g = k(e[1], f, c); else if (e = /\[((?:[\w\-]|\\.)+)\]/.exec(a))g = w(e[1].toLowerCase(), null, f, c); else if (e = /\[((?:[\w\-]|\\.)+)\s*=\s*['"]?((?:\\.|[^'"]+)+)['"]?\]/.exec(a)) {
                g = e[1].toLowerCase();
                e = e[2];
                if (g === "id")f = d(e, f, c); else if (g === "class")f = k(e, f, c); else if (g === "name") {
                    g = [];
                    e = (c.ownerDocument || c).getElementsByName(e.replace(/\\/g, ""));
                    for (var Z, h = 0, l = e.length; h < l; h++)Z = e[h], b(f, Z.nodeName) && Da(c, Z) && Z.getAttribute("name") !== null && g.push(Z);
                    f = g
                } else f =
                    w(g, e, f, c);
                g = f
            } else {
                f = c.getElementsByTagName(f);
                Z = 0;
                for (h = f.length; Z < h; Z++)e = f[Z], e.nodeType == 1 && g.push(e)
            }
            return g
        }

        var f = a.split(",");
        if (f.length > 1) {
            var n = [];
            h(f, function () {
                h(Fa(this, c), function () {
                    e(this, n) < 0 && n.push(this)
                })
            });
            return n
        }
        for (var c = c || document, f = [], l, o = /((?:\\.|[^\s>])+|[\s>])/g; l = o.exec(a);)l[1] !== " " && f.push(l[1]);
        l = [];
        if (f.length == 1)return i(f[0], c);
        var o = !1, m, s, j, r, p, v, q, B, E, u;
        v = 0;
        for (lenth = f.length; v < lenth; v++)if (m = f[v], m === ">")o = !0; else {
            if (v > 0) {
                s = [];
                q = 0;
                for (E = l.length; q < E; q++) {
                    r =
                        l[q];
                    j = i(m, r);
                    B = 0;
                    for (u = j.length; B < u; B++)p = j[B], o ? r === p.parentNode && s.push(p) : s.push(p)
                }
                l = s
            } else l = i(m, c);
            if (l.length === 0)return []
        }
        return l
    }

    function ia(a) {
        if (!a)return document;
        return a.ownerDocument || a.document || a
    }

    function ja(a) {
        if (!a)return b;
        a = ia(a);
        return a.parentWindow || a.defaultView
    }

    function Nb(a, c) {
        if (a.nodeType == 1) {
            var g = ia(a);
            try {
                a.innerHTML = '<img id="__kindeditor_temp_tag__" width="0" height="0" style="display:none;" />' + c;
                var b = g.getElementById("__kindeditor_temp_tag__");
                b.parentNode.removeChild(b)
            } catch (d) {
                i(a).empty(),
                    i("@" + c, g).each(function () {
                        a.appendChild(this)
                    })
            }
        }
    }

    function Oa(a, c, g) {
        F && N < 8 && c.toLowerCase() == "class" && (c = "className");
        a.setAttribute(c, "" + g)
    }

    function Pa(a) {
        if (!a || !a.nodeName)return "";
        return a.nodeName.toLowerCase()
    }

    function Ob(a, c) {
        var g = ja(a), b = p(c), d = "";
        g.getComputedStyle ? (g = g.getComputedStyle(a, null), d = g[b] || g.getPropertyValue(c) || a.style[b]) : a.currentStyle && (d = a.currentStyle[b] || a.style[b]);
        return d
    }

    function X(a) {
        a = a || document;
        return da ? a.body : a.documentElement
    }

    function na(a) {
        var a = a || document,
            c;
        F || Pb || Qa ? (c = X(a).scrollLeft, a = X(a).scrollTop) : (c = ja(a).scrollX, a = ja(a).scrollY);
        return {x: c, y: a}
    }

    function P(a) {
        this.init(a)
    }

    function lb(a) {
        a.collapsed = a.startContainer === a.endContainer && a.startOffset === a.endOffset;
        return a
    }

    function Ra(a, c, g) {
        function b(d, t, V) {
            var k = d.nodeValue.length, i;
            c && (i = d.cloneNode(!0), i = t > 0 ? i.splitText(t) : i, V < k && i.splitText(V - t));
            if (g) {
                var w = d;
                t > 0 && (w = d.splitText(t), a.setStart(d, t));
                V < k && (d = w.splitText(V - t), a.setEnd(d, 0));
                e.push(w)
            }
            return i
        }

        function d() {
            g && a.up().collapse(!0);
            for (var c = 0, b = e.length; c < b; c++) {
                var t = e[c];
                t.parentNode && t.parentNode.removeChild(t)
            }
        }

        function k(d, t) {
            for (var m = d.firstChild, s; m;) {
                s = (new aa(i)).selectNode(m);
                h = s.compareBoundaryPoints(ra, a);
                h >= 0 && l <= 0 && (l = s.compareBoundaryPoints(sa, a));
                l >= 0 && n <= 0 && (n = s.compareBoundaryPoints(oa, a));
                n >= 0 && o <= 0 && (o = s.compareBoundaryPoints(ta, a));
                if (o >= 0)return !1;
                s = m.nextSibling;
                if (h > 0)if (m.nodeType == 1)if (l >= 0 && n <= 0)c && t.appendChild(m.cloneNode(!0)), g && e.push(m); else {
                    var j;
                    c && (j = m.cloneNode(!1), t.appendChild(j));
                    if (k(m,
                            j) === !1)return !1
                } else if (m.nodeType == 3 && (m = m == f.startContainer ? b(m, f.startOffset, m.nodeValue.length) : m == f.endContainer ? b(m, 0, f.endOffset) : b(m, 0, m.nodeValue.length), c))try {
                    t.appendChild(m)
                } catch (r) {
                }
                m = s
            }
        }

        var i = a.doc, e = [], f = a.cloneRange().down(), h = -1, l = -1, n = -1, o = -1, m = a.commonAncestor(), s = i.createDocumentFragment();
        if (m.nodeType == 3)return m = b(m, a.startOffset, a.endOffset), c && s.appendChild(m), d(), c ? s : a;
        k(m, s);
        g && a.up().collapse(!0);
        for (var m = 0, j = e.length; m < j; m++) {
            var r = e[m];
            r.parentNode && r.parentNode.removeChild(r)
        }
        return c ?
            s : a
    }

    function ua(a, c) {
        for (var g = c; g;) {
            var b = i(g);
            if (b.name == "marquee" || b.name == "select")return;
            g = g.parentNode
        }
        try {
            a.moveToElementText(c)
        } catch (d) {
        }
    }

    function mb(a, c) {
        var g = a.parentElement().ownerDocument, b = a.duplicate();
        b.collapse(c);
        var d = b.parentElement(), k = d.childNodes;
        if (k.length === 0)return {node: d.parentNode, offset: i(d).index()};
        var w = g, e = 0, f = -1, h = a.duplicate();
        ua(h, d);
        for (var l = 0, n = k.length; l < n; l++) {
            var o = k[l], f = h.compareEndPoints("StartToStart", b);
            if (f === 0)return {node: o.parentNode, offset: l};
            if (o.nodeType ==
                1) {
                var m = a.duplicate(), s, j = i(o), r = o;
                j.isControl() && (s = g.createElement("span"), j.after(s), r = s, e += j.text().replace(/\r\n|\n|\r/g, "").length);
                ua(m, r);
                h.setEndPoint("StartToEnd", m);
                f > 0 ? e += m.text.replace(/\r\n|\n|\r/g, "").length : e = 0;
                s && i(s).remove()
            } else o.nodeType == 3 && (h.moveStart("character", o.nodeValue.length), e += o.nodeValue.length);
            f < 0 && (w = o)
        }
        if (f < 0 && w.nodeType == 1)return {node: d, offset: i(d.lastChild).index() + 1};
        if (f > 0)for (; w.nextSibling && w.nodeType == 1;)w = w.nextSibling;
        h = a.duplicate();
        ua(h, d);
        h.setEndPoint("StartToEnd",
            b);
        e -= h.text.replace(/\r\n|\n|\r/g, "").length;
        if (f > 0 && w.nodeType == 3)for (g = w.previousSibling; g && g.nodeType == 3;)e -= g.nodeValue.length, g = g.previousSibling;
        return {node: w, offset: e}
    }

    function nb(a, c) {
        var g = a.ownerDocument || a, b = g.body.createTextRange();
        if (g == a)return b.collapse(!0), b;
        if (a.nodeType == 1 && a.childNodes.length > 0) {
            var d = a.childNodes, k;
            c === 0 ? (k = d[0], d = !0) : (k = d[c - 1], d = !1);
            if (!k)return b;
            if (i(k).name === "head")return c === 1 && (d = !0), c === 2 && (d = !1), b.collapse(d), b;
            if (k.nodeType == 1) {
                var w = i(k), e;
                w.isControl() &&
                (e = g.createElement("span"), d ? w.before(e) : w.after(e), k = e);
                ua(b, k);
                b.collapse(d);
                e && i(e).remove();
                return b
            }
            a = k;
            c = d ? 0 : k.nodeValue.length
        }
        g = g.createElement("span");
        i(a).before(g);
        ua(b, g);
        b.moveStart("character", c);
        i(g).remove();
        return b
    }

    function ob(a) {
        function c(a) {
            if (i(a.node).name == "tr")a.node = a.node.cells[a.offset], a.offset = 0
        }

        var g;
        if (Y) {
            if (a.item)return g = ia(a.item(0)), g = new aa(g), g.selectNode(a.item(0)), g;
            g = a.parentElement().ownerDocument;
            var b = mb(a, !0), a = mb(a, !1);
            c(b);
            c(a);
            g = new aa(g);
            g.setStart(b.node,
                b.offset);
            g.setEnd(a.node, a.offset);
            return g
        }
        b = a.startContainer;
        g = b.ownerDocument || b;
        g = new aa(g);
        g.setStart(b, a.startOffset);
        g.setEnd(a.endContainer, a.endOffset);
        return g
    }

    function aa(a) {
        this.init(a)
    }

    function Sa(a) {
        if (!a.nodeName)return a.constructor === aa ? a : ob(a);
        return new aa(a)
    }

    function ea(a, c, g) {
        try {
            a.execCommand(c, !1, g)
        } catch (b) {
        }
    }

    function pb(a, c) {
        var g = "";
        try {
            g = a.queryCommandValue(c)
        } catch (b) {
        }
        typeof g !== "string" && (g = "");
        return g
    }

    function Ta(a) {
        var c = ja(a);
        return Y ? a.selection : c.getSelection()
    }

    function qb(a) {
        var c = {}, g, b;
        h(a, function (a, d) {
            g = a.split(",");
            for (var i = 0, e = g.length; i < e; i++)b = g[i], c[b] = d
        });
        return c
    }

    function Ua(a, c) {
        return rb(a, c, "*") || rb(a, c)
    }

    function rb(a, c, g) {
        g = g || a.name;
        if (a.type !== 1)return !1;
        c = qb(c);
        if (!c[g])return !1;
        for (var g = c[g].split(","), c = 0, b = g.length; c < b; c++) {
            var d = g[c];
            if (d === "*")return !0;
            var k = /^(\.?)([^=]+)(?:=([^=]*))?$/.exec(d), i = k[1] ? "css" : "attr", d = k[2], k = k[3] || "";
            if (k === "" && a[i](d) !== "")return !0;
            if (k !== "" && a[i](d) === k)return !0
        }
        return !1
    }

    function Va(a, c) {
        a.type ==
        1 && (sb(a, c, "*"), sb(a, c))
    }

    function sb(a, c, g) {
        g = g || a.name;
        if (a.type === 1 && (c = qb(c), c[g])) {
            for (var g = c[g].split(","), c = !1, b = 0, d = g.length; b < d; b++) {
                var k = g[b];
                if (k === "*") {
                    c = !0;
                    break
                }
                var i = /^(\.?)([^=]+)(?:=([^=]*))?$/.exec(k), k = i[2];
                i[1] ? (k = p(k), a[0].style[k] && (a[0].style[k] = "")) : a.removeAttr(k)
            }
            c && a.remove(!0)
        }
    }

    function Wa(a) {
        for (; a.first();)a = a.first();
        return a
    }

    function pa(a) {
        if (a.type != 1 || a.isSingle())return !1;
        return a.html().replace(/<[^>]+>/g, "") === ""
    }

    function Qb(a, c, g) {
        h(c, function (c, g) {
            c !== "style" &&
            a.attr(c, g)
        });
        h(g, function (c, g) {
            a.css(c, g)
        })
    }

    function va(a) {
        this.init(a)
    }

    function tb(a) {
        a.nodeName && (a = ia(a), a = Sa(a).selectNodeContents(a.body).collapse(!1));
        return new va(a)
    }

    function Xa(a) {
        var c = a.moveEl, g = a.moveFn, b = a.clickEl || c, t = a.beforeDrag, k = [document];
        (a.iframeFix === d || a.iframeFix) && i("iframe").each(function () {
            if (!/^https?:\/\//.test(R(this.src || "", "absolute"))) {
                var a;
                try {
                    a = Ya(this)
                } catch (c) {
                }
                if (a) {
                    var g = i(this).pos();
                    i(a).data("pos-x", g.x);
                    i(a).data("pos-y", g.y);
                    k.push(a)
                }
            }
        });
        b.mousedown(function (a) {
            function d(a) {
                a.preventDefault();
                var c = i(ia(a.target)), t = fa((c.data("pos-x") || 0) + a.pageX - j), a = fa((c.data("pos-y") || 0) + a.pageY - r);
                g.call(b, n, o, m, s, t, a)
            }

            function e(a) {
                a.preventDefault()
            }

            function f(a) {
                a.preventDefault();
                i(k).unbind("mousemove", d).unbind("mouseup", f).unbind("selectstart", e);
                h.releaseCapture && h.releaseCapture()
            }

            a.stopPropagation();
            var h = b.get(), n = l(c.css("left")), o = l(c.css("top")), m = c.width(), s = c.height(), j = a.pageX, r = a.pageY;
            t && t();
            i(k).mousemove(d).mouseup(f).bind("selectstart", e);
            h.setCapture && h.setCapture()
        })
    }

    function ga(a) {
        this.init(a)
    }

    function Za(a) {
        return new ga(a)
    }

    function Ya(a) {
        a = i(a)[0];
        return a.contentDocument || a.contentWindow.document
    }

    function Rb(a, c, g, b) {
        var d = [$a === "" ? "<html>" : '<html dir="' + $a + '">', '<head><meta charset="utf-8" /><title></title>', "<style>", "html {margin:0;padding:0;}", "body {margin:0;padding:5px;}", 'body, td {font:12px/1.5 "sans serif",tahoma,verdana,helvetica;}', "body, p, div {word-wrap: break-word;}", "p {margin:5px 0;}", "table {border-collapse:collapse;}", "img {border:0;}", "noscript {display:none;}", "table.ke-zeroborder td {border:1px dotted #AAA;}",
            "img.ke-flash {", "\tborder:1px solid #AAA;", "\tbackground-image:url(" + a + "common/flash.gif);", "\tbackground-position:center center;", "\tbackground-repeat:no-repeat;", "\twidth:100px;", "\theight:100px;", "}", "img.ke-rm {", "\tborder:1px solid #AAA;", "\tbackground-image:url(" + a + "common/rm.gif);", "\tbackground-position:center center;", "\tbackground-repeat:no-repeat;", "\twidth:100px;", "\theight:100px;", "}", "img.ke-media {", "\tborder:1px solid #AAA;", "\tbackground-image:url(" + a + "common/media.gif);", "\tbackground-position:center center;",
            "\tbackground-repeat:no-repeat;", "\twidth:100px;", "\theight:100px;", "}", "img.ke-anchor {", "\tborder:1px dashed #666;", "\twidth:16px;", "\theight:16px;", "}", ".ke-script, .ke-noscript, .ke-display-none {", "\tdisplay:none;", "\tfont-size:0;", "\twidth:0;", "\theight:0;", "}", ".ke-pagebreak {", "\tborder:1px dotted #AAA;", "\tfont-size:0;", "\theight:2px;", "}", "</style>"];
        f(g) || (g = [g]);
        h(g, function (a, c) {
            c && d.push('<link href="' + c + '" rel="stylesheet" />')
        });
        b && d.push("<style>" + b + "</style>");
        d.push("</head><body " +
            (c ? 'class="' + c + '"' : "") + "></body></html>");
        return d.join("\n")
    }

    function wa(a, c) {
        if (a.hasVal()) {
            if (c === d) {
                var g = a.val();
                return g = g.replace(/(<(?:p|p\s[^>]*)>) *(<\/p>)/ig, "")
            }
            return a.val(c)
        }
        return a.html(c)
    }

    function xa(a) {
        this.init(a)
    }

    function ub(a) {
        return new xa(a)
    }

    function vb(a, c) {
        var g = this.get(a);
        g && !g.hasClass("ke-disabled") && c(g)
    }

    function Ga(a) {
        this.init(a)
    }

    function wb(a) {
        return new Ga(a)
    }

    function ya(a) {
        this.init(a)
    }

    function ab(a) {
        return new ya(a)
    }

    function za(a) {
        this.init(a)
    }

    function xb(a) {
        return new za(a)
    }

    function bb(a) {
        this.init(a)
    }

    function Aa(a) {
        this.init(a)
    }

    function yb(a) {
        return new Aa(a)
    }

    function cb(a, c) {
        var g = document.getElementsByTagName("head")[0] || (da ? document.body : document.documentElement), b = document.createElement("script");
        g.appendChild(b);
        b.src = a;
        b.charset = "utf-8";
        b.onload = b.onreadystatechange = function () {
            if (!this.readyState || this.readyState === "loaded")c && c(), b.onload = b.onreadystatechange = null, g.removeChild(b)
        }
    }

    function zb(a) {
        var c = a.indexOf("?");
        return c > 0 ? a.substr(0, c) : a
    }

    function db(a) {
        for (var c =
            document.getElementsByTagName("head")[0] || (da ? document.body : document.documentElement), g = document.createElement("link"), b = zb(R(a, "absolute")), d = i('link[rel="stylesheet"]', c), k = 0, w = d.length; k < w; k++)if (zb(R(d[k].href, "absolute")) === b)return;
        c.appendChild(g);
        g.href = a;
        g.rel = "stylesheet"
    }

    function Ab(a, c) {
        if (a === d)return ba;
        if (!c)return ba[a];
        ba[a] = c
    }

    function Bb(a) {
        var c, g = "core";
        if (c = /^(\w+)\.(\w+)$/.exec(a))g = c[1], a = c[2];
        return {ns: g, key: a}
    }

    function Cb(a, c) {
        c = c === d ? i.options.langType : c;
        if (typeof a === "string") {
            if (!ca[c])return "no language";
            var g = a.length - 1;
            if (a.substr(g) === ".")return ca[c][a.substr(0, g)];
            g = Bb(a);
            return ca[c][g.ns][g.key]
        }
        h(a, function (a, g) {
            var b = Bb(a);
            ca[c] || (ca[c] = {});
            ca[c][b.ns] || (ca[c][b.ns] = {});
            ca[c][b.ns][b.key] = g
        })
    }

    function Ha(a, c) {
        if (!a.collapsed) {
            var a = a.cloneRange().up(), g = a.startContainer, b = a.startOffset;
            if (ka || a.isControl())if ((g = i(g.childNodes[b])) && g.name == "img" && c(g))return g
        }
    }

    function Sb() {
        var a = this;
        i(a.edit.doc).contextmenu(function (c) {
            a.menu && a.hideMenu();
            if (a.useContextmenu) {
                if (a._contextmenus.length !==
                    0) {
                    var g = 0, b = [];
                    for (h(a._contextmenus, function () {
                        if (this.title == "-")b.push(this); else if (this.cond && this.cond() && (b.push(this), this.width && this.width > g))g = this.width
                    }); b.length > 0 && b[0].title == "-";)b.shift();
                    for (; b.length > 0 && b[b.length - 1].title == "-";)b.pop();
                    var d = null;
                    h(b, function (a) {
                        this.title == "-" && d.title == "-" && delete b[a];
                        d = this
                    });
                    if (b.length > 0) {
                        c.preventDefault();
                        var k = i(a.edit.iframe).pos(), w = ab({x: k.x + c.clientX, y: k.y + c.clientY, width: g, css: {visibility: "hidden"}, shadowMode: a.shadowMode});
                        h(b,
                            function () {
                                this.title && w.addItem(this)
                            });
                        var k = X(w.doc), e = w.div.height();
                        c.clientY + e >= k.clientHeight - 100 && w.pos(w.x, l(w.y) - e);
                        w.div.css("visibility", "visible");
                        a.menu = w
                    }
                }
            } else c.preventDefault()
        })
    }

    function Tb() {
        function a(a) {
            for (a = i(a.commonAncestor()); a;) {
                if (a.type == 1 && !a.isStyle())break;
                a = a.parent()
            }
            return a.name
        }

        var c = this, g = c.edit.doc, b = c.newlineTag;
        if (!(F && b !== "br") && (!la || !(N < 3 && b !== "p")) && !(Qa && N < 9)) {
            var d = z("h1,h2,h3,h4,h5,h6,pre,li"), k = z("p,h1,h2,h3,h4,h5,h6,pre,li,blockquote");
            i(g).keydown(function (i) {
                if (!(i.which !=
                    13 || i.shiftKey || i.ctrlKey || i.altKey)) {
                    c.cmd.selection();
                    var e = a(c.cmd.range);
                    e == "marquee" || e == "select" || (b === "br" && !d[e] ? (i.preventDefault(), c.insertHtml("<br />" + (F && N < 9 ? "" : "\u200b"))) : k[e] || ea(g, "formatblock", "<p>"))
                }
            });
            i(g).keyup(function (d) {
                if (!(d.which != 13 || d.shiftKey || d.ctrlKey || d.altKey) && b != "br")if (la) {
                    var d = c.cmd.commonAncestor("p"), t = c.cmd.commonAncestor("a");
                    t && t.text() == "" && (t.remove(!0), c.cmd.range.selectNodeContents(d[0]).collapse(!0), c.cmd.select())
                } else if (c.cmd.selection(), d = a(c.cmd.range),
                        !(d == "marquee" || d == "select"))if (k[d] || ea(g, "formatblock", "<p>"), d = c.cmd.commonAncestor("div")) {
                    for (var t = i("<p></p>"), e = d[0].firstChild; e;) {
                        var f = e.nextSibling;
                        t.append(e);
                        e = f
                    }
                    d.before(t);
                    d.remove();
                    c.cmd.range.selectNodeContents(t[0]);
                    c.cmd.select()
                }
            })
        }
    }

    function Ub() {
        var a = this, c = a.edit.doc;
        i(c).keydown(function (g) {
            if (g.which == 9)if (g.preventDefault(), a.afterTab)a.afterTab.call(a, g); else {
                var g = a.cmd, b = g.range;
                b.shrink();
                b.collapsed && b.startContainer.nodeType == 1 && (b.insertNode(i("@&nbsp;", c)[0]), g.select());
                a.insertHtml("&nbsp;&nbsp;&nbsp;&nbsp;")
            }
        })
    }

    function Vb() {
        var a = this;
        i(a.edit.textarea[0], a.edit.win).focus(function (c) {
            a.afterFocus && a.afterFocus.call(a, c)
        }).blur(function (c) {
            a.afterBlur && a.afterBlur.call(a, c)
        })
    }

    function ha(a) {
        return m(a.replace(/<span [^>]*id="?__kindeditor_bookmark_\w+_\d+__"?[^>]*><\/span>/ig, ""))
    }

    function Ia(a) {
        return a.replace(/<div[^>]+class="?__kindeditor_paste__"?[^>]*>[\s\S]*?<\/div>/ig, "")
    }

    function Db(a, c) {
        if (a.length === 0)a.push(c); else {
            var g = a[a.length - 1];
            ha(c.html) !==
            ha(g.html) && a.push(c)
        }
    }

    function Eb(a, c) {
        var g = this.edit, b = g.doc.body, d, k;
        if (a.length === 0)return this;
        g.designMode ? (d = this.cmd.range, k = d.createBookmark(!0), k.html = b.innerHTML) : k = {html: b.innerHTML};
        Db(c, k);
        var e = a.pop();
        ha(k.html) === ha(e.html) && a.length > 0 && (e = a.pop());
        g.designMode ? (g.html(e.html), e.start && (d.moveToBookmark(e), this.select())) : i(b).html(ha(e.html));
        return this
    }

    function Ba(a) {
        function c(a, c) {
            Ba.prototype[a] === d && (g[a] = c);
            g.options[a] = c
        }

        var g = this;
        g.options = {};
        h(a, function (g) {
            c(g, a[g])
        });
        h(i.options, function (a, b) {
            g[a] === d && c(a, b)
        });
        var b = i(g.srcElement || "<textarea/>");
        if (!g.width)g.width = b[0].style.width || b.width();
        if (!g.height)g.height = b[0].style.height || b.height();
        c("width", q(g.width, g.minWidth));
        c("height", q(g.height, g.minHeight));
        c("width", o(g.width));
        c("height", o(g.height));
        if (Wb && (!Xb || N < 534))g.designMode = !1;
        g.srcElement = b;
        g.initContent = "";
        g.plugin = {};
        g.isCreated = !1;
        g._handlers = {};
        g._contextmenus = [];
        g._undoStack = [];
        g._redoStack = [];
        g._firstAddBookmark = !0;
        g.menu = g.contextmenu =
            null;
        g.dialogs = []
    }

    function Fb(a, c) {
        function g(a) {
            h(ba, function (c, g) {
                j(g) && g.call(a, KindEditor)
            });
            return a.create()
        }

        c = c || {};
        c.basePath = q(c.basePath, i.basePath);
        c.themesPath = q(c.themesPath, c.basePath + "themes/");
        c.langPath = q(c.langPath, c.basePath + "lang/");
        c.pluginsPath = q(c.pluginsPath, c.basePath + "plugins/");
        if (q(c.loadStyleMode, i.options.loadStyleMode)) {
            var b = q(c.themeType, i.options.themeType);
            db(c.themesPath + "default/default.css");
            db(c.themesPath + b + "/" + b + ".css")
        }
        if ((b = i(a)) && b.length !== 0) {
            if (b.length >
                1)return b.each(function () {
                Fb(this, c)
            }), _instances[0];
            c.srcElement = b[0];
            var d = new Ba(c);
            _instances.push(d);
            if (ca[d.langType])return g(d);
            cb(d.langPath + d.langType + ".js?ver=" + encodeURIComponent(i.DEBUG ? Ja : Ka), function () {
                g(d)
            });
            return d
        }
    }

    function Ca(a, c) {
        i(a).each(function (a, b) {
            i.each(_instances, function (a, g) {
                if (g && g.srcElement[0] == b)return c.call(g, a), !1
            })
        })
    }

    if (!b.KindEditor) {
        if (!b.console)b.console = {};
        if (!console.log)console.log = function () {
        };
        var Ka = "4.1.10 (2013-11-23)", J = navigator.userAgent.toLowerCase(),
            F = J.indexOf("msie") > -1 && J.indexOf("opera") == -1, Pb = J.indexOf("msie") == -1 && J.indexOf("trident") > -1, la = J.indexOf("gecko") > -1 && J.indexOf("khtml") == -1, ka = J.indexOf("applewebkit") > -1, Qa = J.indexOf("opera") > -1, Wb = J.indexOf("mobile") > -1, Xb = /ipad|iphone|ipod/.test(J), da = document.compatMode != "CSS1Compat", Y = !b.getSelection, N = (J = /(?:msie|firefox|webkit|opera)[\/:\s](\d+)/.exec(J)) ? J[1] : "0", Ja = (new Date).getTime(), fa = Math.round, i = {
                DEBUG: !1,
                VERSION: Ka,
                IE: F,
                GECKO: la,
                WEBKIT: ka,
                OPERA: Qa,
                V: N,
                TIME: Ja,
                each: h,
                isArray: f,
                isFunction: j,
                inArray: e,
                inString: n,
                trim: m,
                addUnit: o,
                removeUnit: l,
                escape: s,
                unescape: v,
                toCamel: p,
                toHex: r,
                toMap: z,
                toArray: D,
                undef: q,
                invalidUrl: function (a) {
                    return !a || /[<>"]/.test(a)
                },
                addParam: function (a, c) {
                    return a.indexOf("?") >= 0 ? a + "&" + c : a + "?" + c
                },
                extend: A,
                json: B
            }, ib = z("a,abbr,acronym,b,basefont,bdo,big,br,button,cite,code,del,dfn,em,font,i,img,input,ins,kbd,label,map,q,s,samp,select,small,span,strike,strong,sub,sup,textarea,tt,u,var"), jb = z("address,applet,blockquote,body,center,dd,dir,div,dl,dt,fieldset,form,frameset,h1,h2,h3,h4,h5,h6,head,hr,html,iframe,ins,isindex,li,map,menu,meta,noframes,noscript,object,ol,p,pre,script,style,table,tbody,td,tfoot,th,thead,title,tr,ul"),
            hb = z("area,base,basefont,br,col,frame,hr,img,input,isindex,link,meta,param,embed"), Gb = z("b,basefont,big,del,em,font,i,s,small,span,strike,strong,sub,sup,u"), Yb = z("img,table,input,textarea,button"), Ma = z("pre,style,script"), La = z("html,head,body,td,tr,table,ol,ul,li");
        z("colgroup,dd,dt,li,options,p,td,tfoot,th,thead,tr");
        var Lb = z("checked,compact,declare,defer,disabled,ismap,multiple,nohref,noresize,noshade,nowrap,readonly,selected"), Hb = z("input,button,textarea,select");
        i.basePath = function () {
            for (var a =
                document.getElementsByTagName("script"), c, g = 0, b = a.length; g < b; g++)if (c = a[g].src || "", /kindeditor[\w\-\.]*\.js/.test(c))return c.substring(0, c.lastIndexOf("/") + 1);
            return ""
        }();
        i.options = {
            designMode: !0,
            fullscreenMode: !1,
            filterMode: !0,
            wellFormatMode: !0,
            shadowMode: !0,
            loadStyleMode: !0,
            basePath: i.basePath,
            themesPath: i.basePath + "themes/",
            langPath: i.basePath + "lang/",
            pluginsPath: i.basePath + "plugins/",
            themeType: "default",
            langType: "zh_CN",
            urlType: "",
            newlineTag: "p",
            resizeType: 2,
            syncType: "form",
            pasteType: 2,
            dialogAlignType: "page",
            useContextmenu: !0,
            fullscreenShortcut: !1,
            bodyClass: "ke-content",
            indentChar: "\t",
            cssPath: "",
            cssData: "",
            minWidth: 650,
            minHeight: 100,
            minChangeSize: 50,
            zIndex: 811213,
            items: ["source", "|", "undo", "redo", "|", "preview", "print", "template", "code", "cut", "copy", "paste", "plainpaste", "wordpaste", "|", "justifyleft", "justifycenter", "justifyright", "justifyfull", "insertorderedlist", "insertunorderedlist", "indent", "outdent", "subscript", "superscript", "clearhtml", "quickformat", "selectall", "|", "fullscreen", "/", "formatblock",
                "fontname", "fontsize", "|", "forecolor", "hilitecolor", "bold", "italic", "underline", "strikethrough", "lineheight", "removeformat", "|", "image", "multiimage", "flash", "media", "insertfile", "table", "hr", "emoticons", "baidumap", "pagebreak", "anchor", "link", "unlink", "|", "about"],
            noDisableItems: ["source", "fullscreen"],
            colorTable: [["#E53333", "#E56600", "#FF9900", "#64451D", "#DFC5A4", "#FFE500"], ["#009900", "#006600", "#99BB00", "#B8D100", "#60D978", "#00D5FF"], ["#337FE5", "#003399", "#4C33E5", "#9933E5", "#CC33E5", "#EE33EE"],
                ["#FFFFFF", "#CCCCCC", "#999999", "#666666", "#333333", "#000000"]],
            fontSizeTable: ["9px", "10px", "12px", "14px", "16px", "18px", "24px", "32px"],
            htmlTags: {
                font: ["id", "class", "color", "size", "face", ".background-color"],
                span: ["id", "class", ".color", ".background-color", ".font-size", ".font-family", ".background", ".font-weight", ".font-style", ".text-decoration", ".vertical-align", ".line-height"],
                div: ["id", "class", "align", ".border", ".margin", ".padding", ".text-align", ".color", ".background-color", ".font-size", ".font-family",
                    ".font-weight", ".background", ".font-style", ".text-decoration", ".vertical-align", ".margin-left"],
                table: ["id", "class", "border", "cellspacing", "cellpadding", "width", "height", "align", "bordercolor", ".padding", ".margin", ".border", "bgcolor", ".text-align", ".color", ".background-color", ".font-size", ".font-family", ".font-weight", ".font-style", ".text-decoration", ".background", ".width", ".height", ".border-collapse"],
                "td,th": ["id", "class", "align", "valign", "width", "height", "colspan", "rowspan", "bgcolor", ".text-align",
                    ".color", ".background-color", ".font-size", ".font-family", ".font-weight", ".font-style", ".text-decoration", ".vertical-align", ".background", ".border"],
                a: ["id", "class", "href", "target", "name"],
                embed: ["id", "class", "src", "width", "height", "type", "loop", "autostart", "quality", ".width", ".height", "align", "allowscriptaccess"],
                img: ["id", "class", "src", "width", "height", "border", "alt", "title", "align", ".width", ".height", ".border"],
                "p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6": ["id", "class", "align", ".text-align", ".color",
                    ".background-color", ".font-size", ".font-family", ".background", ".font-weight", ".font-style", ".text-decoration", ".vertical-align", ".text-indent", ".margin-left"],
                pre: ["id", "class"],
                hr: ["id", "class", ".page-break-after"],
                "br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del": ["id", "class"],
                iframe: ["id", "class", "src", "frameborder", "width", "height", ".width", ".height"]
            },
            layout: '<div class="container"><div class="toolbar"></div><div class="edit"></div><div class="statusbar"></div></div>'
        };
        var fb = !1, Ib = z("8,9,13,32,46,48..57,59,61,65..90,106,109..111,188,190..192,219..222"),
            J = z("33..40"), eb = {};
        h(Ib, function (a, c) {
            eb[a] = c
        });
        h(J, function (a, c) {
            eb[a] = c
        });
        var Zb = "altKey,attrChange,attrName,bubbles,button,cancelable,charCode,clientX,clientY,ctrlKey,currentTarget,data,detail,eventPhase,fromElement,handler,keyCode,metaKey,newValue,offsetX,offsetY,originalTarget,pageX,pageY,prevValue,relatedNode,relatedTarget,screenX,screenY,shiftKey,srcElement,target,toElement,view,wheelDelta,which".split(",");
        A(u, {
            init: function (a, c) {
                var g = this, b = a.ownerDocument || a.document || a;
                g.event = c;
                h(Zb,
                    function (a, b) {
                        g[b] = c[b]
                    });
                if (!g.target)g.target = g.srcElement || b;
                if (g.target.nodeType === 3)g.target = g.target.parentNode;
                if (!g.relatedTarget && g.fromElement)g.relatedTarget = g.fromElement === g.target ? g.toElement : g.fromElement;
                if (g.pageX == null && g.clientX != null) {
                    var t = b.documentElement, b = b.body;
                    g.pageX = g.clientX + (t && t.scrollLeft || b && b.scrollLeft || 0) - (t && t.clientLeft || b && b.clientLeft || 0);
                    g.pageY = g.clientY + (t && t.scrollTop || b && b.scrollTop || 0) - (t && t.clientTop || b && b.clientTop || 0)
                }
                if (!g.which && (g.charCode || g.charCode ===
                    0 ? g.charCode : g.keyCode))g.which = g.charCode || g.keyCode;
                if (!g.metaKey && g.ctrlKey)g.metaKey = g.ctrlKey;
                if (!g.which && g.button !== d)g.which = g.button & 1 ? 1 : g.button & 2 ? 3 : g.button & 4 ? 2 : 0;
                switch (g.which) {
                    case 186:
                        g.which = 59;
                        break;
                    case 187:
                    case 107:
                    case 43:
                        g.which = 61;
                        break;
                    case 189:
                    case 45:
                        g.which = 109;
                        break;
                    case 42:
                        g.which = 106;
                        break;
                    case 47:
                        g.which = 111;
                        break;
                    case 78:
                        g.which = 110
                }
                g.which >= 96 && g.which <= 105 && (g.which -= 48)
            }, preventDefault: function () {
                var a = this.event;
                a.preventDefault ? a.preventDefault() : a.returnValue = !1
            }, stopPropagation: function () {
                var a = this.event;
                a.stopPropagation ? a.stopPropagation() : a.cancelBubble = !0
            }, stop: function () {
                this.preventDefault();
                this.stopPropagation()
            }
        });
        var ma = "kindeditor_" + Ja, gb = 0, L = {}, Jb = !1;
        F && b.attachEvent("onunload", function () {
            h(L, function (a, c) {
                c.el && T(c.el)
            })
        });
        i.ctrl = $;
        i.ready = function (a) {
            function c() {
                t || (t = !0, a(KindEditor), Jb = !0)
            }

            function g() {
                if (!t) {
                    try {
                        document.documentElement.doScroll("left")
                    } catch (a) {
                        setTimeout(g, 100);
                        return
                    }
                    c()
                }
            }

            function d() {
                document.readyState === "complete" &&
                c()
            }

            if (Jb)a(KindEditor); else {
                var t = !1;
                if (document.addEventListener)E(document, "DOMContentLoaded", c); else if (document.attachEvent) {
                    E(document, "readystatechange", d);
                    var k = !1;
                    try {
                        k = b.frameElement == null
                    } catch (i) {
                    }
                    document.documentElement.doScroll && k && g()
                }
                E(b, "load", c)
            }
        };
        i.formatUrl = R;
        i.formatHtml = H;
        i.getCssList = M;
        i.getAttrList = K;
        i.mediaType = W;
        i.mediaAttrs = S;
        i.mediaEmbed = Na;
        i.mediaImg = kb;
        i.clearMsWord = U;
        i.tmpl = function (a, c) {
            var g = new Function("obj", "var p=[],print=function(){p.push.apply(p,arguments);};with(obj){p.push('" +
                a.replace(/[\r\t\n]/g, " ").split("<%").join("\t").replace(/((^|%>)[^\t]*)'/g, "$1\r").replace(/\t=(.*?)%>/g, "',$1,'").split("\t").join("');").split("%>").join("p.push('").split("\r").join("\\'") + "');}return p.join('');");
            return c ? g(c) : g
        };
        J = document.createElement("div");
        J.setAttribute("className", "t");
        var Mb = J.className !== "t";
        i.query = function (a, c) {
            var g = Fa(a, c);
            return g.length > 0 ? g[0] : null
        };
        i.queryAll = Fa;
        A(P, {
            init: function (a) {
                for (var a = f(a) ? a : [a], c = 0, g = 0, b = a.length; g < b; g++)a[g] && (this[g] = a[g].constructor ===
                P ? a[g][0] : a[g], c++);
                this.length = c;
                this.doc = ia(this[0]);
                this.name = Pa(this[0]);
                this.type = this.length > 0 ? this[0].nodeType : null;
                this.win = ja(this[0])
            }, each: function (a) {
                for (var c = 0; c < this.length; c++)if (a.call(this[c], c, this[c]) === !1)break;
                return this
            }, bind: function (a, c) {
                this.each(function () {
                    E(this, a, c)
                });
                return this
            }, unbind: function (a, c) {
                this.each(function () {
                    T(this, a, c)
                });
                return this
            }, fire: function (a) {
                if (this.length < 1)return this;
                qa(this[0], a);
                return this
            }, hasAttr: function (a) {
                if (this.length < 1)return !1;
                return !!Ea(this[0],
                    a)
            }, attr: function (a, c) {
                var g = this;
                if (a === d)return K(g.outer());
                if (typeof a === "object")return h(a, function (a, c) {
                    g.attr(a, c)
                }), g;
                if (c === d)return c = g.length < 1 ? null : Ea(g[0], a), c === null ? "" : c;
                g.each(function () {
                    Oa(this, a, c)
                });
                return g
            }, removeAttr: function (a) {
                this.each(function () {
                    var c = a;
                    F && N < 8 && c.toLowerCase() == "class" && (c = "className");
                    Oa(this, c, "");
                    this.removeAttribute(c)
                });
                return this
            }, get: function (a) {
                if (this.length < 1)return null;
                return this[a || 0]
            }, eq: function (a) {
                if (this.length < 1)return null;
                return this[a] ?
                    new P(this[a]) : null
            }, hasClass: function (a) {
                if (this.length < 1)return !1;
                return n(a, this[0].className, " ")
            }, addClass: function (a) {
                this.each(function () {
                    if (!n(a, this.className, " "))this.className = m(this.className + " " + a)
                });
                return this
            }, removeClass: function (a) {
                this.each(function () {
                    if (n(a, this.className, " "))this.className = m(this.className.replace(RegExp("(^|\\s)" + a + "(\\s|$)"), " "))
                });
                return this
            }, html: function (a) {
                if (a === d) {
                    if (this.length < 1 || this.type != 1)return "";
                    return H(this[0].innerHTML)
                }
                this.each(function () {
                    Nb(this,
                        a)
                });
                return this
            }, text: function () {
                if (this.length < 1)return "";
                return F ? this[0].innerText : this[0].textContent
            }, hasVal: function () {
                if (this.length < 1)return !1;
                return !!Hb[Pa(this[0])]
            }, val: function (a) {
                if (a === d) {
                    if (this.length < 1)return "";
                    return this.hasVal() ? this[0].value : this.attr("value")
                } else return this.each(function () {
                    Hb[Pa(this)] ? this.value = a : Oa(this, "value", a)
                }), this
            }, css: function (a, c) {
                var g = this;
                if (a === d)return M(g.attr("style"));
                if (typeof a === "object")return h(a, function (a, c) {
                    g.css(a, c)
                }), g;
                if (c ===
                    d) {
                    if (g.length < 1)return "";
                    return g[0].style[p(a)] || Ob(g[0], a) || ""
                }
                g.each(function () {
                    this.style[p(a)] = c
                });
                return g
            }, width: function (a) {
                if (a === d) {
                    if (this.length < 1)return 0;
                    return this[0].offsetWidth
                }
                return this.css("width", o(a))
            }, height: function (a) {
                if (a === d) {
                    if (this.length < 1)return 0;
                    return this[0].offsetHeight
                }
                return this.css("height", o(a))
            }, opacity: function (a) {
                this.each(function () {
                    this.style.opacity === d ? this.style.filter = a == 1 ? "" : "alpha(opacity=" + a * 100 + ")" : this.style.opacity = a == 1 ? "" : a
                });
                return this
            },
            data: function (a, c) {
                a = "kindeditor_data_" + a;
                if (c === d) {
                    if (this.length < 1)return null;
                    return this[0][a]
                }
                this.each(function () {
                    this[a] = c
                });
                return this
            }, pos: function () {
                var a = this[0], c = 0, g = 0;
                if (a)if (a.getBoundingClientRect)a = a.getBoundingClientRect(), g = na(this.doc), c = a.left + g.x, g = a.top + g.y; else for (; a;)c += a.offsetLeft, g += a.offsetTop, a = a.offsetParent;
                return {x: fa(c), y: fa(g)}
            }, clone: function (a) {
                if (this.length < 1)return new P([]);
                return new P(this[0].cloneNode(a))
            }, append: function (a) {
                this.each(function () {
                    this.appendChild &&
                    this.appendChild(i(a)[0])
                });
                return this
            }, appendTo: function (a) {
                this.each(function () {
                    i(a)[0].appendChild(this)
                });
                return this
            }, before: function (a) {
                this.each(function () {
                    this.parentNode.insertBefore(i(a)[0], this)
                });
                return this
            }, after: function (a) {
                this.each(function () {
                    this.nextSibling ? this.parentNode.insertBefore(i(a)[0], this.nextSibling) : this.parentNode.appendChild(i(a)[0])
                });
                return this
            }, replaceWith: function (a) {
                var c = [];
                this.each(function (g, b) {
                    T(b);
                    var d = i(a)[0];
                    b.parentNode.replaceChild(d, b);
                    c.push(d)
                });
                return i(c)
            }, empty: function () {
                this.each(function (a, c) {
                    for (var b = c.firstChild; b;) {
                        if (!c.parentNode)break;
                        var d = b.nextSibling;
                        b.parentNode.removeChild(b);
                        b = d
                    }
                });
                return this
            }, remove: function (a) {
                var c = this;
                c.each(function (b, d) {
                    if (d.parentNode) {
                        T(d);
                        if (a)for (var i = d.firstChild; i;) {
                            var k = i.nextSibling;
                            d.parentNode.insertBefore(i, d);
                            i = k
                        }
                        d.parentNode.removeChild(d);
                        delete c[b]
                    }
                });
                c.length = 0;
                return c
            }, show: function (a) {
                a === d && (a = this._originDisplay || "");
                if (this.css("display") != "none")return this;
                return this.css("display",
                    a)
            }, hide: function () {
                if (this.length < 1)return this;
                this._originDisplay = this[0].style.display;
                return this.css("display", "none")
            }, outer: function () {
                if (this.length < 1)return "";
                var a = this.doc.createElement("div");
                a.appendChild(this[0].cloneNode(!0));
                return H(a.innerHTML)
            }, isSingle: function () {
                return !!hb[this.name]
            }, isInline: function () {
                return !!ib[this.name]
            }, isBlock: function () {
                return !!jb[this.name]
            }, isStyle: function () {
                return !!Gb[this.name]
            }, isControl: function () {
                return !!Yb[this.name]
            }, contains: function (a) {
                if (this.length <
                    1)return !1;
                return Da(this[0], i(a)[0])
            }, parent: function () {
                if (this.length < 1)return null;
                var a = this[0].parentNode;
                return a ? new P(a) : null
            }, children: function () {
                if (this.length < 1)return new P([]);
                for (var a = [], c = this[0].firstChild; c;)(c.nodeType != 3 || m(c.nodeValue) !== "") && a.push(c), c = c.nextSibling;
                return new P(a)
            }, first: function () {
                var a = this.children();
                return a.length > 0 ? a.eq(0) : null
            }, last: function () {
                var a = this.children();
                return a.length > 0 ? a.eq(a.length - 1) : null
            }, index: function () {
                if (this.length < 1)return -1;
                for (var a =
                    -1, c = this[0]; c;)a++, c = c.previousSibling;
                return a
            }, prev: function () {
                if (this.length < 1)return null;
                var a = this[0].previousSibling;
                return a ? new P(a) : null
            }, next: function () {
                if (this.length < 1)return null;
                var a = this[0].nextSibling;
                return a ? new P(a) : null
            }, scan: function (a, c) {
                function b(d) {
                    for (d = c ? d.firstChild : d.lastChild; d;) {
                        var i = c ? d.nextSibling : d.previousSibling;
                        if (a(d) === !1)return !1;
                        if (b(d) === !1)return !1;
                        d = i
                    }
                }

                if (!(this.length < 1))return c = c === d ? !0 : c, b(this[0]), this
            }
        });
        h("blur,focus,focusin,focusout,load,resize,scroll,unload,click,dblclick,mousedown,mouseup,mousemove,mouseover,mouseout,mouseenter,mouseleave,change,select,submit,keydown,keypress,keyup,error,contextmenu".split(","),
            function (a, c) {
                P.prototype[c] = function (a) {
                    return a ? this.bind(c, a) : this.fire(c)
                }
            });
        J = i;
        i = function (a, c) {
            function b(a) {
                a[0] || (a = []);
                return new P(a)
            }

            if (!(a === d || a === null)) {
                if (typeof a === "string") {
                    c && (c = i(c)[0]);
                    var e = a.length;
                    a.charAt(0) === "@" && (a = a.substr(1));
                    if (a.length !== e || /<.+>/.test(a)) {
                        var e = (c ? c.ownerDocument || c : document).createElement("div"), t = [];
                        e.innerHTML = '<img id="__kindeditor_temp_tag__" width="0" height="0" style="display:none;" />' + a;
                        for (var k = 0, w = e.childNodes.length; k < w; k++) {
                            var h = e.childNodes[k];
                            h.id != "__kindeditor_temp_tag__" && t.push(h)
                        }
                        return b(t)
                    }
                    return b(Fa(a, c))
                }
                if (a && a.constructor === P)return a;
                a.toArray && (a = a.toArray());
                if (f(a))return b(a);
                return b(D(arguments))
            }
        };
        h(J, function (a, c) {
            i[a] = c
        });
        i.NodeClass = P;
        b.KindEditor = i;
        var sa = 0, ra = 1, oa = 2, ta = 3, Kb = 0;
        A(aa, {
            init: function (a) {
                this.startContainer = a;
                this.startOffset = 0;
                this.endContainer = a;
                this.endOffset = 0;
                this.collapsed = !0;
                this.doc = a
            }, commonAncestor: function () {
                function a(a) {
                    for (var c = []; a;)c.push(a), a = a.parentNode;
                    return c
                }

                for (var c = a(this.startContainer),
                         b = a(this.endContainer), d = 0, i = c.length, k = b.length, e, f; ++d;)if (e = c[i - d], f = b[k - d], !e || !f || e !== f)break;
                return c[i - d + 1]
            }, setStart: function (a, c) {
                var b = this.doc;
                this.startContainer = a;
                this.startOffset = c;
                if (this.endContainer === b)this.endContainer = a, this.endOffset = c;
                return lb(this)
            }, setEnd: function (a, c) {
                var b = this.doc;
                this.endContainer = a;
                this.endOffset = c;
                if (this.startContainer === b)this.startContainer = a, this.startOffset = c;
                return lb(this)
            }, setStartBefore: function (a) {
                return this.setStart(a.parentNode || this.doc,
                    i(a).index())
            }, setStartAfter: function (a) {
                return this.setStart(a.parentNode || this.doc, i(a).index() + 1)
            }, setEndBefore: function (a) {
                return this.setEnd(a.parentNode || this.doc, i(a).index())
            }, setEndAfter: function (a) {
                return this.setEnd(a.parentNode || this.doc, i(a).index() + 1)
            }, selectNode: function (a) {
                return this.setStartBefore(a).setEndAfter(a)
            }, selectNodeContents: function (a) {
                var c = i(a);
                if (c.type == 3 || c.isSingle())return this.selectNode(a);
                c = c.children();
                if (c.length > 0)return this.setStartBefore(c[0]).setEndAfter(c[c.length -
                1]);
                return this.setStart(a, 0).setEnd(a, 0)
            }, collapse: function (a) {
                if (a)return this.setEnd(this.startContainer, this.startOffset);
                return this.setStart(this.endContainer, this.endOffset)
            }, compareBoundaryPoints: function (a, c) {
                var b = this.get(), d = c.get();
                if (Y) {
                    var t = {};
                    t[sa] = "StartToStart";
                    t[ra] = "EndToStart";
                    t[oa] = "EndToEnd";
                    t[ta] = "StartToEnd";
                    b = b.compareEndPoints(t[a], d);
                    if (b !== 0)return b;
                    var k, e, f, h;
                    if (a === sa || a === ta)k = this.startContainer, f = this.startOffset;
                    if (a === ra || a === oa)k = this.endContainer, f = this.endOffset;
                    if (a === sa || a === ra)e = c.startContainer, h = c.startOffset;
                    if (a === oa || a === ta)e = c.endContainer, h = c.endOffset;
                    if (k === e)return k = f - h, k > 0 ? 1 : k < 0 ? -1 : 0;
                    for (b = e; b && b.parentNode !== k;)b = b.parentNode;
                    if (b)return i(b).index() >= f ? -1 : 1;
                    for (b = k; b && b.parentNode !== e;)b = b.parentNode;
                    if (b)return i(b).index() >= h ? 1 : -1;
                    if ((b = i(e).next()) && b.contains(k))return 1;
                    if ((b = i(k).next()) && b.contains(e))return -1
                } else return b.compareBoundaryPoints(a, d)
            }, cloneRange: function () {
                return (new aa(this.doc)).setStart(this.startContainer, this.startOffset).setEnd(this.endContainer,
                    this.endOffset)
            }, toString: function () {
                var a = this.get();
                return (Y ? a.text : a.toString()).replace(/\r\n|\n|\r/g, "")
            }, cloneContents: function () {
                return Ra(this, !0, !1)
            }, deleteContents: function () {
                return Ra(this, !1, !0)
            }, extractContents: function () {
                return Ra(this, !0, !0)
            }, insertNode: function (a) {
                var c = this.startContainer, b = this.startOffset, d = this.endContainer, i = this.endOffset, k, e, f, h = 1;
                if (a.nodeName.toLowerCase() === "#document-fragment")k = a.firstChild, e = a.lastChild, h = a.childNodes.length;
                c.nodeType == 1 ? (f = c.childNodes[b]) ?
                    (c.insertBefore(a, f), c === d && (i += h)) : c.appendChild(a) : c.nodeType == 3 && (b === 0 ? (c.parentNode.insertBefore(a, c), c.parentNode === d && (i += h)) : b >= c.nodeValue.length ? c.nextSibling ? c.parentNode.insertBefore(a, c.nextSibling) : c.parentNode.appendChild(a) : (f = b > 0 ? c.splitText(b) : c, c.parentNode.insertBefore(a, f), c === d && (d = f, i -= b)));
                k ? this.setStartBefore(k).setEndAfter(e) : this.selectNode(a);
                if (this.compareBoundaryPoints(oa, this.cloneRange().setEnd(d, i)) >= 1)return this;
                return this.setEnd(d, i)
            }, surroundContents: function (a) {
                a.appendChild(this.extractContents());
                return this.insertNode(a).selectNode(a)
            }, isControl: function () {
                var a = this.startContainer, c = this.startOffset, b = this.endContainer, d = this.endOffset;
                return a.nodeType == 1 && a === b && c + 1 === d && i(a.childNodes[c]).isControl()
            }, get: function (a) {
                var c = this.doc;
                if (!Y) {
                    c = c.createRange();
                    try {
                        c.setStart(this.startContainer, this.startOffset), c.setEnd(this.endContainer, this.endOffset)
                    } catch (b) {
                    }
                    return c
                }
                if (a && this.isControl())return c = c.body.createControlRange(), c.addElement(this.startContainer.childNodes[this.startOffset]),
                    c;
                a = this.cloneRange().down();
                c = c.body.createTextRange();
                c.setEndPoint("StartToStart", nb(a.startContainer, a.startOffset));
                c.setEndPoint("EndToStart", nb(a.endContainer, a.endOffset));
                return c
            }, html: function () {
                return i(this.cloneContents()).outer()
            }, down: function () {
                function a(a, b, d) {
                    if (a.nodeType == 1 && (a = i(a).children(), a.length !== 0)) {
                        var k, e, f, h;
                        b > 0 && (k = a.eq(b - 1));
                        b < a.length && (e = a.eq(b));
                        if (k && k.type == 3)f = k[0], h = f.nodeValue.length;
                        e && e.type == 3 && (f = e[0], h = 0);
                        f && (d ? c.setStart(f, h) : c.setEnd(f, h))
                    }
                }

                var c =
                    this;
                a(c.startContainer, c.startOffset, !0);
                a(c.endContainer, c.endOffset, !1);
                return c
            }, up: function () {
                function a(a, b, d) {
                    a.nodeType == 3 && (b === 0 ? d ? c.setStartBefore(a) : c.setEndBefore(a) : b == a.nodeValue.length && (d ? c.setStartAfter(a) : c.setEndAfter(a)))
                }

                var c = this;
                a(c.startContainer, c.startOffset, !0);
                a(c.endContainer, c.endOffset, !1);
                return c
            }, enlarge: function (a) {
                function c(c, d, k) {
                    c = i(c);
                    if (!(c.type == 3 || La[c.name] || !a && c.isBlock()))if (d === 0) {
                        for (; !c.prev();) {
                            d = c.parent();
                            if (!d || La[d.name] || !a && d.isBlock())break;
                            c = d
                        }
                        k ? b.setStartBefore(c[0]) : b.setEndBefore(c[0])
                    } else if (d == c.children().length) {
                        for (; !c.next();) {
                            d = c.parent();
                            if (!d || La[d.name] || !a && d.isBlock())break;
                            c = d
                        }
                        k ? b.setStartAfter(c[0]) : b.setEndAfter(c[0])
                    }
                }

                var b = this;
                b.up();
                c(b.startContainer, b.startOffset, !0);
                c(b.endContainer, b.endOffset, !1);
                return b
            }, shrink: function () {
                for (var a, c = this.collapsed; this.startContainer.nodeType == 1 && (a = this.startContainer.childNodes[this.startOffset]) && a.nodeType == 1 && !i(a).isSingle();)this.setStart(a, 0);
                if (c)return this.collapse(c);
                for (; this.endContainer.nodeType == 1 && this.endOffset > 0 && (a = this.endContainer.childNodes[this.endOffset - 1]) && a.nodeType == 1 && !i(a).isSingle();)this.setEnd(a, a.childNodes.length);
                return this
            }, createBookmark: function (a) {
                var c, b = i('<span style="display:none;"></span>', this.doc)[0];
                b.id = "__kindeditor_bookmark_start_" + Kb++ + "__";
                if (!this.collapsed)c = b.cloneNode(!0), c.id = "__kindeditor_bookmark_end_" + Kb++ + "__";
                c && this.cloneRange().collapse(!1).insertNode(c).setEndBefore(c);
                this.insertNode(b).setStartAfter(b);
                return {start: a ? "#" + b.id : b, end: c ? a ? "#" + c.id : c : null}
            }, moveToBookmark: function (a) {
                var c = this.doc, b = i(a.start, c), a = a.end ? i(a.end, c) : null;
                if (!b || b.length < 1)return this;
                this.setStartBefore(b[0]);
                b.remove();
                a && a.length > 0 ? (this.setEndBefore(a[0]), a.remove()) : this.collapse(!0);
                return this
            }, dump: function () {
                console.log("--------------------");
                console.log(this.startContainer.nodeType == 3 ? this.startContainer.nodeValue : this.startContainer, this.startOffset);
                console.log(this.endContainer.nodeType == 3 ? this.endContainer.nodeValue :
                    this.endContainer, this.endOffset)
            }
        });
        i.RangeClass = aa;
        i.range = Sa;
        i.START_TO_START = sa;
        i.START_TO_END = ra;
        i.END_TO_END = oa;
        i.END_TO_START = ta;
        A(va, {
            init: function (a) {
                var c = a.doc;
                this.doc = c;
                this.win = ja(c);
                this.sel = Ta(c);
                this.range = a
            }, selection: function (a) {
                var c = this.doc, b;
                b = Ta(c);
                var d;
                try {
                    d = b.rangeCount > 0 ? b.getRangeAt(0) : b.createRange()
                } catch (e) {
                }
                b = Y && (!d || !d.item && d.parentElement().ownerDocument !== c) ? null : d;
                this.sel = Ta(c);
                if (b)return this.range = Sa(b), i(this.range.startContainer).name == "html" && this.range.selectNodeContents(c.body).collapse(!1),
                    this;
                a && this.range.selectNodeContents(c.body).collapse(!1);
                return this
            }, select: function (a) {
                var a = q(a, !0), c = this.sel, b = this.range.cloneRange().shrink(), d = b.startContainer, e = b.startOffset, k = ia(d), f = this.win, h, l = !1;
                if (a && d.nodeType == 1 && b.collapsed) {
                    if (Y) {
                        c = i("<span>&nbsp;</span>", k);
                        b.insertNode(c[0]);
                        h = k.body.createTextRange();
                        try {
                            h.moveToElementText(c[0])
                        } catch (n) {
                        }
                        h.collapse(!1);
                        h.select();
                        c.remove();
                        f.focus();
                        return this
                    }
                    if (ka && (a = d.childNodes, i(d).isInline() || e > 0 && i(a[e - 1]).isInline() || a[e] && i(a[e]).isInline()))b.insertNode(k.createTextNode("\u200b")),
                        l = !0
                }
                if (Y)try {
                    h = b.get(!0), h.select()
                } catch (o) {
                } else l && b.collapse(!1), h = b.get(!0), c.removeAllRanges(), c.addRange(h), k !== document && (b = i(h.endContainer).pos(), f.scrollTo(b.x, b.y));
                f.focus();
                return this
            }, wrap: function (a) {
                var c = this.range, b;
                b = i(a, this.doc);
                if (c.collapsed)return c.shrink(), c.insertNode(b[0]).selectNodeContents(b[0]), this;
                if (b.isBlock()) {
                    for (var d = a = b.clone(!0); d.first();)d = d.first();
                    d.append(c.extractContents());
                    c.insertNode(a[0]).selectNode(a[0]);
                    return this
                }
                c.enlarge();
                var e = c.createBookmark(),
                    a = c.commonAncestor(), k = !1;
                i(a).scan(function (a) {
                    if (!k && a == e.start)k = !0; else if (k) {
                        if (a == e.end)return !1;
                        var c = i(a), d;
                        a:{
                            for (d = c; d && d.name != "body";) {
                                if (Ma[d.name] || d.name == "div" && d.hasClass("ke-script")) {
                                    d = !0;
                                    break a
                                }
                                d = d.parent()
                            }
                            d = !1
                        }
                        if (!d && c.type == 3 && m(a.nodeValue).length > 0) {
                            for (var f; (f = c.parent()) && f.isStyle() && f.children().length == 1;)c = f;
                            f = b;
                            f = f.clone(!0);
                            if (c.type == 3)Wa(f).append(c.clone(!1)), c.replaceWith(f); else {
                                for (var a = c, h; (h = c.first()) && h.children().length == 1;)c = h;
                                h = c.first();
                                for (c = c.doc.createDocumentFragment(); h;)c.appendChild(h[0]),
                                    h = h.next();
                                h = a.clone(!0);
                                d = Wa(h);
                                for (var l = h, n = !1; f;) {
                                    for (; l;)l.name === f.name && (Qb(l, f.attr(), f.css()), n = !0), l = l.first();
                                    n || d.append(f.clone(!1));
                                    n = !1;
                                    f = f.first()
                                }
                                f = h;
                                c.firstChild && Wa(f).append(c);
                                a.replaceWith(f)
                            }
                        }
                    }
                });
                c.moveToBookmark(e);
                return this
            }, split: function (a, c) {
                for (var b = this.range, d = b.doc, e = b.cloneRange().collapse(a), k = e.startContainer, f = e.startOffset, h = k.nodeType == 3 ? k.parentNode : k, l = !1, n; h && h.parentNode;) {
                    n = i(h);
                    if (c) {
                        if (!n.isStyle())break;
                        if (!Ua(n, c))break
                    } else if (La[n.name])break;
                    l = !0;
                    h = h.parentNode
                }
                if (l)d = d.createElement("span"), b.cloneRange().collapse(!a).insertNode(d), a ? e.setStartBefore(h.firstChild).setEnd(k, f) : e.setStart(k, f).setEndAfter(h.lastChild), k = e.extractContents(), f = k.firstChild, l = k.lastChild, a ? (e.insertNode(k), b.setStartAfter(l).setEndBefore(d)) : (h.appendChild(k), b.setStartBefore(d).setEndBefore(f)), e = d.parentNode, e == b.endContainer && (h = i(d).prev(), k = i(d).next(), h && k && h.type == 3 && k.type == 3 ? b.setEnd(h[0], h[0].nodeValue.length) : a || b.setEnd(b.endContainer, b.endOffset -
                    1)), e.removeChild(d);
                return this
            }, remove: function (a) {
                var c = this.doc, b = this.range;
                b.enlarge();
                if (b.startOffset === 0) {
                    for (var d = i(b.startContainer), e; (e = d.parent()) && e.isStyle() && e.children().length == 1;)d = e;
                    b.setStart(d[0], 0);
                    d = i(b.startContainer);
                    d.isBlock() && Va(d, a);
                    (d = d.parent()) && d.isBlock() && Va(d, a)
                }
                if (b.collapsed) {
                    this.split(!0, a);
                    c = b.startContainer;
                    d = b.startOffset;
                    if (d > 0 && (e = i(c.childNodes[d - 1])) && pa(e))e.remove(), b.setStart(c, d - 1);
                    (d = i(c.childNodes[d])) && pa(d) && d.remove();
                    pa(c) && (b.startBefore(c),
                        c.remove());
                    b.collapse(!0);
                    return this
                }
                this.split(!0, a);
                this.split(!1, a);
                var k = c.createElement("span"), f = c.createElement("span");
                b.cloneRange().collapse(!1).insertNode(f);
                b.cloneRange().collapse(!0).insertNode(k);
                var l = [], n = !1;
                i(b.commonAncestor()).scan(function (a) {
                    if (!n && a == k)n = !0; else {
                        if (a == f)return !1;
                        n && l.push(a)
                    }
                });
                i(k).remove();
                i(f).remove();
                c = b.startContainer;
                d = b.startOffset;
                e = b.endContainer;
                var o = b.endOffset;
                if (d > 0) {
                    var m = i(c.childNodes[d - 1]);
                    m && pa(m) && (m.remove(), b.setStart(c, d - 1), c == e && b.setEnd(e,
                        o - 1));
                    if ((d = i(c.childNodes[d])) && pa(d))d.remove(), c == e && b.setEnd(e, o - 1)
                }
                (c = i(e.childNodes[b.endOffset])) && pa(c) && c.remove();
                c = b.createBookmark(!0);
                h(l, function (c, b) {
                    Va(i(b), a)
                });
                b.moveToBookmark(c);
                return this
            }, commonNode: function (a) {
                function c(c) {
                    for (var b = c; c;) {
                        if (Ua(i(c), a))return i(c);
                        c = c.parentNode
                    }
                    for (; b && (b = b.lastChild);)if (Ua(i(b), a))return i(b);
                    return null
                }

                var b = this.range, d = b.endContainer, b = b.endOffset, e = d.nodeType == 3 || b === 0 ? d : d.childNodes[b - 1], k = c(e);
                if (k)return k;
                if (e.nodeType == 1 || d.nodeType ==
                    3 && b === 0)if (d = i(e).prev())return c(d);
                return null
            }, commonAncestor: function (a) {
                function c(c) {
                    for (; c;) {
                        if (c.nodeType == 1 && c.tagName.toLowerCase() === a)return c;
                        c = c.parentNode
                    }
                    return null
                }

                var b = this.range, d = b.startContainer, e = b.startOffset, k = b.endContainer, b = b.endOffset, k = k.nodeType == 3 || b === 0 ? k : k.childNodes[b - 1], d = c(d.nodeType == 3 || e === 0 ? d : d.childNodes[e - 1]), e = c(k);
                if (d && e && d === e)return i(d);
                return null
            }, state: function (a) {
                var c = this.doc, b = !1;
                try {
                    b = c.queryCommandState(a)
                } catch (d) {
                }
                return b
            }, val: function (a) {
                var c =
                    this.doc, a = a.toLowerCase(), b = "";
                if (a === "fontfamily" || a === "fontname")return b = pb(c, "fontname"), b = b.replace(/['"]/g, ""), b.toLowerCase();
                if (a === "formatblock") {
                    b = pb(c, a);
                    if (b === "" && (a = this.commonNode({"h1,h2,h3,h4,h5,h6,p,div,pre,address": "*"})))b = a.name;
                    b === "Normal" && (b = "p");
                    return b.toLowerCase()
                }
                if (a === "fontsize")return (a = this.commonNode({"*": ".font-size"})) && (b = a.css("font-size")), b.toLowerCase();
                if (a === "forecolor")return (a = this.commonNode({"*": ".color"})) && (b = a.css("color")), b = r(b), b === "" && (b = "default"),
                    b.toLowerCase();
                if (a === "hilitecolor")return (a = this.commonNode({"*": ".background-color"})) && (b = a.css("background-color")), b = r(b), b === "" && (b = "default"), b.toLowerCase();
                return b
            }, toggle: function (a, c) {
                this.commonNode(c) ? this.remove(c) : this.wrap(a);
                return this.select()
            }, bold: function () {
                return this.toggle("<strong></strong>", {span: ".font-weight=bold", strong: "*", b: "*"})
            }, italic: function () {
                return this.toggle("<em></em>", {span: ".font-style=italic", em: "*", i: "*"})
            }, underline: function () {
                return this.toggle("<u></u>",
                    {span: ".text-decoration=underline", u: "*"})
            }, strikethrough: function () {
                return this.toggle("<s></s>", {span: ".text-decoration=line-through", s: "*"})
            }, forecolor: function (a) {
                return this.wrap('<span style="color:' + a + ';"></span>').select()
            }, hilitecolor: function (a) {
                return this.wrap('<span style="background-color:' + a + ';"></span>').select()
            }, fontsize: function (a) {
                return this.wrap('<span style="font-size:' + a + ';"></span>').select()
            }, fontname: function (a) {
                return this.fontfamily(a)
            }, fontfamily: function (a) {
                return this.wrap('<span style="font-family:' +
                    a + ';"></span>').select()
            }, removeformat: function () {
                var a = {"*": ".font-weight,.font-style,.text-decoration,.color,.background-color,.font-size,.font-family,.text-indent"};
                h(Gb, function (c) {
                    a[c] = "*"
                });
                this.remove(a);
                return this.select()
            }, inserthtml: function (a, c) {
                function b(a, c) {
                    var c = '<img id="__kindeditor_temp_tag__" width="0" height="0" style="display:none;" />' + c, d = a.get();
                    d.item ? d.item(0).outerHTML = c : d.pasteHTML(c);
                    var g = a.doc.getElementById("__kindeditor_temp_tag__");
                    g.parentNode.removeChild(g);
                    d =
                        ob(d);
                    a.setEnd(d.endContainer, d.endOffset);
                    a.collapse(!1);
                    e.select(!1)
                }

                function d(a, c) {
                    var b = a.doc, g = b.createDocumentFragment();
                    i("@" + c, b).each(function () {
                        g.appendChild(this)
                    });
                    a.deleteContents();
                    a.insertNode(g);
                    a.collapse(!1);
                    e.select(!1)
                }

                var e = this, k = e.range;
                if (a === "")return e;
                if (Y && c) {
                    try {
                        b(k, a)
                    } catch (f) {
                        d(k, a)
                    }
                    return e
                }
                d(k, a);
                return e
            }, hr: function () {
                return this.inserthtml("<hr />")
            }, print: function () {
                this.win.print();
                return this
            }, insertimage: function (a, c, b, d, e, i) {
                c = q(c, "");
                q(e, 0);
                a = '<img src="' +
                    s(a) + '" data-ke-src="' + s(a) + '" ';
                b && (a += 'width="' + s(b) + '" ');
                d && (a += 'height="' + s(d) + '" ');
                c && (a += 'title="' + s(c) + '" ');
                i && (a += 'align="' + s(i) + '" ');
                a += 'alt="' + s(c) + '" ';
                a += "/>";
                return this.inserthtml(a)
            }, createlink: function (a, c) {
                function b(a, c, d) {
                    i(a).attr("href", c).attr("data-ke-src", c);
                    d ? i(a).attr("target", d) : i(a).removeAttr("target")
                }

                var d = this.doc, e = this.range;
                this.select();
                var k = this.commonNode({a: "*"});
                k && !e.isControl() && (e.selectNode(k.get()), this.select());
                k = '<a href="' + s(a) + '" data-ke-src="' +
                    s(a) + '" ';
                c && (k += ' target="' + s(c) + '"');
                if (e.collapsed)return k += ">" + s(a) + "</a>", this.inserthtml(k);
                if (e.isControl()) {
                    var f = i(e.startContainer.childNodes[e.startOffset]);
                    k += "></a>";
                    f.after(i(k, d));
                    f.next().append(f);
                    e.selectNode(f[0]);
                    return this.select()
                }
                var k = e.startContainer, f = e.startOffset, h = e.endContainer, e = e.endOffset;
                if (k.nodeType == 1 && k === h && f + 1 === e && (e = k.childNodes[f], e.nodeName.toLowerCase() == "a"))return b(e, a, c), this;
                ea(d, "createlink", "__kindeditor_temp_url__");
                i('a[href="__kindeditor_temp_url__"]',
                    d).each(function () {
                        b(this, a, c)
                    });
                return this
            }, unlink: function () {
                var a = this.doc, c = this.range;
                this.select();
                if (c.collapsed) {
                    var b = this.commonNode({a: "*"});
                    b && (c.selectNode(b.get()), this.select());
                    ea(a, "unlink", null);
                    ka && i(c.startContainer).name === "img" && (a = i(c.startContainer).parent(), a.name === "a" && a.remove(!0))
                } else ea(a, "unlink", null);
                return this
            }
        });
        h("formatblock,selectall,justifyleft,justifycenter,justifyright,justifyfull,insertorderedlist,insertunorderedlist,indent,outdent,subscript,superscript".split(","),
            function (a, c) {
                va.prototype[c] = function (a) {
                    this.select();
                    ea(this.doc, c, a);
                    Y && e(c, "justifyleft,justifycenter,justifyright,justifyfull".split(",")) >= 0 && this.selection();
                    (!Y || e(c, "formatblock,selectall,insertorderedlist,insertunorderedlist".split(",")) >= 0) && this.selection();
                    return this
                }
            });
        h("cut,copy,paste".split(","), function (a, c) {
            va.prototype[c] = function () {
                if (!this.doc.queryCommandSupported(c))throw"not supported";
                this.select();
                ea(this.doc, c, null);
                return this
            }
        });
        i.CmdClass = va;
        i.cmd = tb;
        A(ga, {
            init: function (a) {
                var c =
                    this;
                c.name = a.name || "";
                c.doc = a.doc || document;
                c.win = ja(c.doc);
                c.x = o(a.x);
                c.y = o(a.y);
                c.z = a.z;
                c.width = o(a.width);
                c.height = o(a.height);
                c.div = i('<div style="display:block;"></div>');
                c.options = a;
                c._alignEl = a.alignEl;
                c.width && c.div.css("width", c.width);
                c.height && c.div.css("height", c.height);
                c.z && c.div.css({position: "absolute", left: c.x, top: c.y, "z-index": c.z});
                c.z && (c.x === d || c.y === d) && c.autoPos(c.width, c.height);
                a.cls && c.div.addClass(a.cls);
                a.shadowMode && c.div.addClass("ke-shadow");
                a.css && c.div.css(a.css);
                a.src ? i(a.src).replaceWith(c.div) : i(c.doc.body).append(c.div);
                a.html && c.div.html(a.html);
                if (a.autoScroll)if (F && N < 7 || da) {
                    var b = na();
                    i(c.win).bind("scroll", function () {
                        var a = na(), d = a.x - b.x, a = a.y - b.y;
                        c.pos(l(c.x) + d, l(c.y) + a, !1)
                    })
                } else c.div.css("position", "fixed")
            }, pos: function (a, c, b) {
                b = q(b, !0);
                if (a !== null && (a = a < 0 ? 0 : o(a), this.div.css("left", a), b))this.x = a;
                if (c !== null && (c = c < 0 ? 0 : o(c), this.div.css("top", c), b))this.y = c;
                return this
            }, autoPos: function (a, c) {
                var b = l(a) || 0, d = l(c) || 0, e = na();
                if (this._alignEl) {
                    var k =
                        i(this._alignEl), f = k.pos(), b = fa(k[0].clientWidth / 2 - b / 2), d = fa(k[0].clientHeight / 2 - d / 2);
                    x = b < 0 ? f.x : f.x + b;
                    y = d < 0 ? f.y : f.y + d
                } else f = X(this.doc), x = fa(e.x + (f.clientWidth - b) / 2), y = fa(e.y + (f.clientHeight - d) / 2);
                F && N < 7 || da || (x -= e.x, y -= e.y);
                return this.pos(x, y)
            }, remove: function () {
                var a = this;
                (F && N < 7 || da) && i(a.win).unbind("scroll");
                a.div.remove();
                h(a, function (c) {
                    a[c] = null
                });
                return this
            }, show: function () {
                this.div.show();
                return this
            }, hide: function () {
                this.div.hide();
                return this
            }, draggable: function (a) {
                var c = this, a = a ||
                    {};
                a.moveEl = c.div;
                a.moveFn = function (a, b, d, e, i, f) {
                    if ((a += i) < 0)a = 0;
                    if ((b += f) < 0)b = 0;
                    c.pos(a, b)
                };
                Xa(a);
                return c
            }
        });
        i.WidgetClass = ga;
        i.widget = Za;
        var $a = "";
        if (J = document.getElementsByTagName("html"))$a = J[0].dir;
        A(xa, ga, {
            init: function (a) {
                function c() {
                    var c = Ya(b.iframe);
                    c.open();
                    if (h)c.domain = document.domain;
                    c.write(Rb(d, e, k, f));
                    c.close();
                    b.win = b.iframe[0].contentWindow;
                    b.doc = c;
                    var l = tb(c);
                    b.afterChange(function () {
                        l.selection()
                    });
                    ka && i(c).click(function (a) {
                        i(a.target).name === "img" && (l.selection(!0), l.range.selectNode(a.target),
                            l.select())
                    });
                    if (F)b._mousedownHandler = function () {
                        var a = l.range.cloneRange();
                        a.shrink();
                        a.isControl() && b.blur()
                    }, i(document).mousedown(b._mousedownHandler), i(c).keydown(function (a) {
                        if (a.which == 8) {
                            l.selection();
                            var c = l.range;
                            c.isControl() && (c.collapse(!0), i(c.startContainer.childNodes[c.startOffset]).remove(), a.preventDefault())
                        }
                    });
                    b.cmd = l;
                    b.html(wa(b.srcElement));
                    F ? (c.body.disabled = !0, c.body.contentEditable = !0, c.body.removeAttribute("disabled")) : c.designMode = "on";
                    a.afterCreate && a.afterCreate.call(b)
                }

                var b = this;
                xa.parent.init.call(b, a);
                b.srcElement = i(a.srcElement);
                b.div.addClass("ke-edit");
                b.designMode = q(a.designMode, !0);
                b.beforeGetHtml = a.beforeGetHtml;
                b.beforeSetHtml = a.beforeSetHtml;
                b.afterSetHtml = a.afterSetHtml;
                var d = q(a.themesPath, ""), e = a.bodyClass, k = a.cssPath, f = a.cssData, h = location.protocol != "res:" && location.host.replace(/:\d+/, "") !== document.domain, l = "document.open();" + (h ? 'document.domain="' + document.domain + '";' : "") + "document.close();", l = F ? ' src="javascript:void(function(){' + encodeURIComponent(l) +
                '}())"' : "";
                b.iframe = i('<iframe class="ke-edit-iframe" hidefocus="true" frameborder="0"' + l + "></iframe>").css("width", "100%");
                b.textarea = i('<textarea class="ke-edit-textarea" hidefocus="true"></textarea>').css("width", "100%");
                b.tabIndex = isNaN(parseInt(a.tabIndex, 10)) ? b.srcElement.attr("tabindex") : parseInt(a.tabIndex, 10);
                b.iframe.attr("tabindex", b.tabIndex);
                b.textarea.attr("tabindex", b.tabIndex);
                b.width && b.setWidth(b.width);
                b.height && b.setHeight(b.height);
                b.designMode ? b.textarea.hide() : b.iframe.hide();
                h && b.iframe.bind("load", function () {
                    b.iframe.unbind("load");
                    F ? c() : setTimeout(c, 0)
                });
                b.div.append(b.iframe);
                b.div.append(b.textarea);
                b.srcElement.hide();
                !h && c()
            }, setWidth: function (a) {
                this.width = a = o(a);
                this.div.css("width", a);
                return this
            }, setHeight: function (a) {
                this.height = a = o(a);
                this.div.css("height", a);
                this.iframe.css("height", a);
                if (F && N < 8 || da)a = o(l(a) - 2);
                this.textarea.css("height", a);
                return this
            }, remove: function () {
                var a = this.doc;
                i(a.body).unbind();
                i(a).unbind();
                i(this.win).unbind();
                this._mousedownHandler &&
                i(document).unbind("mousedown", this._mousedownHandler);
                wa(this.srcElement, this.html());
                this.srcElement.show();
                a.write("");
                this.iframe.unbind();
                this.textarea.unbind();
                xa.parent.remove.call(this)
            }, html: function (a, c) {
                var b = this.doc;
                if (this.designMode) {
                    b = b.body;
                    if (a === d)return a = c ? "<!doctype html><html>" + b.parentNode.innerHTML + "</html>" : b.innerHTML, this.beforeGetHtml && (a = this.beforeGetHtml(a)), la && a == "<br />" && (a = ""), a;
                    this.beforeSetHtml && (a = this.beforeSetHtml(a));
                    F && N >= 9 && (a = a.replace(/(<.*?checked=")checked(".*>)/ig,
                        "$1$2"));
                    i(b).html(a);
                    this.afterSetHtml && this.afterSetHtml();
                    return this
                }
                if (a === d)return this.textarea.val();
                this.textarea.val(a);
                return this
            }, design: function (a) {
                if (a === d ? !this.designMode : a) {
                    if (!this.designMode)a = this.html(), this.designMode = !0, this.html(a), this.textarea.hide(), this.iframe.show()
                } else if (this.designMode)a = this.html(), this.designMode = !1, this.html(a), this.iframe.hide(), this.textarea.show();
                return this.focus()
            }, focus: function () {
                this.designMode ? this.win.focus() : this.textarea[0].focus();
                return this
            }, blur: function () {
                if (F) {
                    var a = i('<input type="text" style="float:left;width:0;height:0;padding:0;margin:0;border:0;" value="" />', this.div);
                    this.div.append(a);
                    a[0].focus();
                    a.remove()
                } else this.designMode ? this.win.blur() : this.textarea[0].blur();
                return this
            }, afterChange: function (a) {
                function c(c) {
                    setTimeout(function () {
                        a(c)
                    }, 1)
                }

                var b = this.doc, d = b.body;
                i(b).keyup(function (c) {
                    !c.ctrlKey && !c.altKey && eb[c.which] && a(c)
                });
                i(b).mouseup(a).contextmenu(a);
                i(this.win).blur(a);
                i(d).bind("paste", c);
                i(d).bind("cut",
                    c);
                return this
            }
        });
        i.EditClass = xa;
        i.edit = ub;
        i.iframeDoc = Ya;
        A(Ga, ga, {
            init: function (a) {
                function c(a) {
                    a = i(a);
                    if (a.hasClass("ke-outline"))return a;
                    if (a.hasClass("ke-toolbar-icon"))return a.parent()
                }

                function b(a, d) {
                    var g = c(a.target);
                    if (g && !g.hasClass("ke-disabled") && !g.hasClass("ke-selected"))g[d]("ke-on")
                }

                var d = this;
                Ga.parent.init.call(d, a);
                d.disableMode = q(a.disableMode, !1);
                d.noDisableItemMap = z(q(a.noDisableItems, []));
                d._itemMap = {};
                d.div.addClass("ke-toolbar").bind("contextmenu,mousedown,mousemove", function (a) {
                    a.preventDefault()
                }).attr("unselectable",
                    "on");
                d.div.mouseover(function (a) {
                    b(a, "addClass")
                }).mouseout(function (a) {
                    b(a, "removeClass")
                }).click(function (a) {
                    var b = c(a.target);
                    b && !b.hasClass("ke-disabled") && d.options.click.call(this, a, b.attr("data-name"))
                })
            }, get: function (a) {
                if (this._itemMap[a])return this._itemMap[a];
                return this._itemMap[a] = i("span.ke-icon-" + a, this.div).parent()
            }, select: function (a) {
                vb.call(this, a, function (a) {
                    a.addClass("ke-selected")
                });
                return self
            }, unselect: function (a) {
                vb.call(this, a, function (a) {
                    a.removeClass("ke-selected").removeClass("ke-on")
                });
                return self
            }, enable: function (a) {
                if (a = a.get ? a : this.get(a))a.removeClass("ke-disabled"), a.opacity(1);
                return this
            }, disable: function (a) {
                if (a = a.get ? a : this.get(a))a.removeClass("ke-selected").addClass("ke-disabled"), a.opacity(0.5);
                return this
            }, disableAll: function (a, c) {
                var b = this, e = b.noDisableItemMap;
                c && (e = z(c));
                (a === d ? !b.disableMode : a) ? (i("span.ke-outline", b.div).each(function () {
                    var a = i(this), c = a[0].getAttribute("data-name", 2);
                    e[c] || b.disable(a)
                }), b.disableMode = !0) : (i("span.ke-outline", b.div).each(function () {
                    var a =
                        i(this), c = a[0].getAttribute("data-name", 2);
                    e[c] || b.enable(a)
                }), b.disableMode = !1);
                return b
            }
        });
        i.ToolbarClass = Ga;
        i.toolbar = wb;
        A(ya, ga, {
            init: function (a) {
                a.z = a.z || 811213;
                ya.parent.init.call(this, a);
                this.centerLineMode = q(a.centerLineMode, !0);
                this.div.addClass("ke-menu").bind("click,mousedown", function (a) {
                    a.stopPropagation()
                }).attr("unselectable", "on")
            }, addItem: function (a) {
                if (a.title === "-")this.div.append(i('<div class="ke-menu-separator"></div>')); else {
                    var c = i('<div class="ke-menu-item" unselectable="on"></div>'),
                        b = i('<div class="ke-inline-block ke-menu-item-left"></div>'), d = i('<div class="ke-inline-block ke-menu-item-right"></div>'), e = o(a.height), f = q(a.iconClass, "");
                    this.div.append(c);
                    e && (c.css("height", e), d.css("line-height", e));
                    var h;
                    this.centerLineMode && (h = i('<div class="ke-inline-block ke-menu-item-center"></div>'), e && h.css("height", e));
                    c.mouseover(function () {
                        i(this).addClass("ke-menu-item-on");
                        h && h.addClass("ke-menu-item-center-on")
                    }).mouseout(function () {
                        i(this).removeClass("ke-menu-item-on");
                        h && h.removeClass("ke-menu-item-center-on")
                    }).click(function (c) {
                        a.click.call(i(this));
                        c.stopPropagation()
                    }).append(b);
                    h && c.append(h);
                    c.append(d);
                    a.checked && (f = "ke-icon-checked");
                    f !== "" && b.html('<span class="ke-inline-block ke-toolbar-icon ke-toolbar-icon-url ' + f + '"></span>');
                    d.html(a.title);
                    return this
                }
            }, remove: function () {
                this.options.beforeRemove && this.options.beforeRemove.call(this);
                i(".ke-menu-item", this.div[0]).unbind();
                ya.parent.remove.call(this);
                return this
            }
        });
        i.MenuClass = ya;
        i.menu = ab;
        A(za, ga, {
            init: function (a) {
                a.z = a.z || 811213;
                za.parent.init.call(this, a);
                var c = a.colors || [["#E53333",
                        "#E56600", "#FF9900", "#64451D", "#DFC5A4", "#FFE500"], ["#009900", "#006600", "#99BB00", "#B8D100", "#60D978", "#00D5FF"], ["#337FE5", "#003399", "#4C33E5", "#9933E5", "#CC33E5", "#EE33EE"], ["#FFFFFF", "#CCCCCC", "#999999", "#666666", "#333333", "#000000"]];
                this.selectedColor = (a.selectedColor || "").toLowerCase();
                this._cells = [];
                this.div.addClass("ke-colorpicker").bind("click,mousedown", function (a) {
                    a.stopPropagation()
                }).attr("unselectable", "on");
                a = this.doc.createElement("table");
                this.div.append(a);
                a.className = "ke-colorpicker-table";
                a.cellPadding = 0;
                a.cellSpacing = 0;
                a.border = 0;
                var b = a.insertRow(0), d = b.insertCell(0);
                d.colSpan = c[0].length;
                this._addAttr(d, "", "ke-colorpicker-cell-top");
                for (var e = 0; e < c.length; e++)for (var b = a.insertRow(e + 1), i = 0; i < c[e].length; i++)d = b.insertCell(i), this._addAttr(d, c[e][i], "ke-colorpicker-cell")
            }, _addAttr: function (a, c, b) {
                var d = this, a = i(a).addClass(b);
                d.selectedColor === c.toLowerCase() && a.addClass("ke-colorpicker-cell-selected");
                a.attr("title", c || d.options.noColor);
                a.mouseover(function () {
                    i(this).addClass("ke-colorpicker-cell-on")
                });
                a.mouseout(function () {
                    i(this).removeClass("ke-colorpicker-cell-on")
                });
                a.click(function (a) {
                    a.stop();
                    d.options.click.call(i(this), c)
                });
                c ? a.append(i('<div class="ke-colorpicker-cell-color" unselectable="on"></div>').css("background-color", c)) : a.html(d.options.noColor);
                i(a).attr("unselectable", "on");
                d._cells.push(a)
            }, remove: function () {
                h(this._cells, function () {
                    this.unbind()
                });
                za.parent.remove.call(this);
                return this
            }
        });
        i.ColorPickerClass = za;
        i.colorpicker = xb;
        A(bb, {
            init: function (a) {
                var c = i(a.button), b = a.fieldName ||
                    "file", d = a.url || "", e = c.val(), f = a.extraParams || {}, h = c[0].className || "", l = a.target || "kindeditor_upload_iframe_" + (new Date).getTime();
                a.afterError = a.afterError || function (a) {
                        alert(a)
                    };
                var n = [], o;
                for (o in f)n.push('<input type="hidden" name="' + o + '" value="' + f[o] + '" />');
                b = ['<div class="ke-inline-block ' + h + '">', a.target ? "" : '<iframe name="' + l + '" style="display:none;"></iframe>', a.form ? '<div class="ke-upload-area">' : '<form class="ke-upload-area ke-form" method="post" enctype="multipart/form-data" target="' +
                l + '" action="' + d + '">', '<span class="ke-button-common">', n.join(""), '<input type="button" class="ke-button-common ke-button" value="' + e + '" />', "</span>", '<input type="file" class="ke-upload-file" name="' + b + '" tabindex="-1" />', a.form ? "</div>" : "</form>", "</div>"].join("");
                b = i(b, c.doc);
                c.hide();
                c.before(b);
                this.div = b;
                this.button = c;
                this.iframe = a.target ? i('iframe[name="' + l + '"]') : i("iframe", b);
                this.form = a.form ? i(a.form) : i("form", b);
                this.fileBox = i(".ke-upload-file", b);
                c = a.width || i(".ke-button-common", b).width();
                i(".ke-upload-area", b).width(c);
                this.options = a
            }, submit: function () {
                var a = this, c = a.iframe;
                c.bind("load", function () {
                    c.unbind();
                    var b = document.createElement("form");
                    a.fileBox.before(b);
                    i(b).append(a.fileBox);
                    b.reset();
                    i(b).remove(!0);
                    var b = i.iframeDoc(c), d = b.getElementsByTagName("pre")[0], e = "", f, e = d ? d.innerHTML : b.body.innerHTML, e = v(e);
                    c[0].src = "javascript:false";
                    try {
                        f = i.json(e)
                    } catch (h) {
                        a.options.afterError.call(a, "<!doctype html><html>" + b.body.parentNode.innerHTML + "</html>")
                    }
                    f && a.options.afterUpload.call(a,
                        f)
                });
                a.form[0].submit();
                return a
            }, remove: function () {
                this.fileBox && this.fileBox.unbind();
                this.iframe.remove();
                this.div.remove();
                this.button.show();
                return this
            }
        });
        i.UploadButtonClass = bb;
        i.uploadbutton = function (a) {
            return new bb(a)
        };
        A(Aa, ga, {
            init: function (a) {
                var c = q(a.shadowMode, !0);
                a.z = a.z || 811213;
                a.shadowMode = !1;
                a.autoScroll = q(a.autoScroll, !0);
                Aa.parent.init.call(this, a);
                var b = a.title, d = i(a.body, this.doc), e = a.previewBtn, f = a.yesBtn, n = a.noBtn, o = a.closeBtn, m = q(a.showMask, !0);
                this.div.addClass("ke-dialog").bind("click,mousedown",
                    function (a) {
                        a.stopPropagation()
                    });
                var s = i('<div class="ke-dialog-content"></div>').appendTo(this.div);
                F && N < 7 ? this.iframeMask = i('<iframe src="about:blank" class="ke-dialog-shadow"></iframe>').appendTo(this.div) : c && i('<div class="ke-dialog-shadow"></div>').appendTo(this.div);
                c = i('<div class="ke-dialog-header"></div>');
                s.append(c);
                c.html(b);
                this.closeIcon = i('<span class="ke-dialog-icon-close" title="' + o.name + '"></span>').click(o.click);
                c.append(this.closeIcon);
                this.draggable({clickEl: c, beforeDrag: a.beforeDrag});
                a = i('<div class="ke-dialog-body"></div>');
                s.append(a);
                a.append(d);
                var j = i('<div class="ke-dialog-footer"></div>');
                (e || f || n) && s.append(j);
                h([{btn: e, name: "preview"}, {btn: f, name: "yes"}, {btn: n, name: "no"}], function () {
                    if (this.btn) {
                        var a = this.btn, a = a || {}, c = a.name || "", b = i('<span class="ke-button-common ke-button-outer" title="' + c + '"></span>'), c = i('<input class="ke-button-common ke-button" type="button" value="' + c + '" />');
                        a.click && c.click(a.click);
                        b.append(c);
                        b.addClass("ke-dialog-" + this.name);
                        j.append(b)
                    }
                });
                this.height && a.height(l(this.height) - c.height() - j.height());
                this.div.width(this.div.width());
                this.div.height(this.div.height());
                this.mask = null;
                if (m)d = X(this.doc), this.mask = Za({
                    x: 0,
                    y: 0,
                    z: this.z - 1,
                    cls: "ke-dialog-mask",
                    width: Math.max(d.scrollWidth, d.clientWidth),
                    height: Math.max(d.scrollHeight, d.clientHeight)
                });
                this.autoPos(this.div.width(), this.div.height());
                this.footerDiv = j;
                this.bodyDiv = a;
                this.headerDiv = c;
                this.isLoading = !1
            }, setMaskIndex: function (a) {
                this.mask.div.css("z-index", a)
            }, showLoading: function (a) {
                var a =
                    q(a, ""), c = this.bodyDiv;
                this.loading = i('<div class="ke-dialog-loading"><div class="ke-inline-block ke-dialog-loading-content" style="margin-top:' + Math.round(c.height() / 3) + 'px;">' + a + "</div></div>").width(c.width()).height(c.height()).css("top", this.headerDiv.height() + "px");
                c.css("visibility", "hidden").after(this.loading);
                this.isLoading = !0;
                return this
            }, hideLoading: function () {
                this.loading && this.loading.remove();
                this.bodyDiv.css("visibility", "visible");
                this.isLoading = !1;
                return this
            }, remove: function () {
                this.options.beforeRemove &&
                this.options.beforeRemove.call(this);
                this.mask && this.mask.remove();
                this.iframeMask && this.iframeMask.remove();
                this.closeIcon.unbind();
                i("input", this.div).unbind();
                i("button", this.div).unbind();
                this.footerDiv.unbind();
                this.bodyDiv.unbind();
                this.headerDiv.unbind();
                i("iframe", this.div).each(function () {
                    i(this).remove()
                });
                Aa.parent.remove.call(this);
                return this
            }
        });
        i.DialogClass = Aa;
        i.dialog = yb;
        i.tabs = function (a) {
            var c = Za(a), b = c.remove, d = a.afterSelect, a = c.div, e = [];
            a.addClass("ke-tabs").bind("contextmenu,mousedown,mousemove",
                function (a) {
                    a.preventDefault()
                });
            var f = i('<ul class="ke-tabs-ul ke-clearfix"></ul>');
            a.append(f);
            c.add = function (a) {
                var c = i('<li class="ke-tabs-li">' + a.title + "</li>");
                c.data("tab", a);
                e.push(c);
                f.append(c)
            };
            c.selectedIndex = 0;
            c.select = function (a) {
                c.selectedIndex = a;
                h(e, function (b, d) {
                    d.unbind();
                    b === a ? (d.addClass("ke-tabs-li-selected"), i(d.data("tab").panel).show("")) : (d.removeClass("ke-tabs-li-selected").removeClass("ke-tabs-li-on").mouseover(function () {
                        i(this).addClass("ke-tabs-li-on")
                    }).mouseout(function () {
                        i(this).removeClass("ke-tabs-li-on")
                    }).click(function () {
                        c.select(b)
                    }),
                        i(d.data("tab").panel).hide())
                });
                d && d.call(c, a)
            };
            c.remove = function () {
                h(e, function () {
                    this.remove()
                });
                f.remove();
                b.call(c)
            };
            return c
        };
        i.loadScript = cb;
        i.loadStyle = db;
        i.ajax = function (a, c, d, e, i) {
            var d = d || "GET", i = i || "json", f = b.XMLHttpRequest ? new b.XMLHttpRequest : new ActiveXObject("Microsoft.XMLHTTP");
            f.open(d, a, !0);
            f.onreadystatechange = function () {
                if (f.readyState == 4 && f.status == 200 && c) {
                    var a = m(f.responseText);
                    i == "json" && (a = B(a));
                    c(a)
                }
            };
            if (d == "POST") {
                var l = [];
                h(e, function (a, c) {
                    l.push(encodeURIComponent(a) +
                        "=" + encodeURIComponent(c))
                });
                try {
                    f.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
                } catch (n) {
                }
                f.send(l.join("&"))
            } else f.send(null)
        };
        var ba = {}, ca = {};
        Ba.prototype = {
            lang: function (a) {
                return Cb(a, this.langType)
            }, loadPlugin: function (a, c) {
                var b = this;
                if (ba[a]) {
                    if (!j(ba[a]))return setTimeout(function () {
                        b.loadPlugin(a, c)
                    }, 100), b;
                    ba[a].call(b, KindEditor);
                    c && c.call(b);
                    return b
                }
                ba[a] = "loading";
                cb(b.pluginsPath + a + "/" + a + ".js?ver=" + encodeURIComponent(i.DEBUG ? Ja : Ka), function () {
                    setTimeout(function () {
                        ba[a] &&
                        b.loadPlugin(a, c)
                    }, 0)
                });
                return b
            }, handler: function (a, c) {
                var b = this;
                b._handlers[a] || (b._handlers[a] = []);
                if (j(c))return b._handlers[a].push(c), b;
                h(b._handlers[a], function () {
                    c = this.call(b, c)
                });
                return c
            }, clickToolbar: function (a, c) {
                var b = this, e = "clickToolbar" + a;
                if (c === d) {
                    if (b._handlers[e])return b.handler(e);
                    b.loadPlugin(a, function () {
                        b.handler(e)
                    });
                    return b
                }
                return b.handler(e, c)
            }, updateState: function () {
                var a = this;
                h("justifyleft,justifycenter,justifyright,justifyfull,insertorderedlist,insertunorderedlist,subscript,superscript,bold,italic,underline,strikethrough".split(","),
                    function (c, b) {
                        a.cmd.state(b) ? a.toolbar.select(b) : a.toolbar.unselect(b)
                    });
                return a
            }, addContextmenu: function (a) {
                this._contextmenus.push(a);
                return this
            }, afterCreate: function (a) {
                return this.handler("afterCreate", a)
            }, beforeRemove: function (a) {
                return this.handler("beforeRemove", a)
            }, beforeGetHtml: function (a) {
                return this.handler("beforeGetHtml", a)
            }, beforeSetHtml: function (a) {
                return this.handler("beforeSetHtml", a)
            }, afterSetHtml: function (a) {
                return this.handler("afterSetHtml", a)
            }, create: function () {
                function a() {
                    m.height() ===
                    0 ? setTimeout(a, 100) : c.resize(e, f, !1)
                }

                var c = this, d = c.fullscreenMode;
                if (c.isCreated)return c;
                if (c.srcElement.data("kindeditor"))return c;
                c.srcElement.data("kindeditor", "true");
                d ? X().style.overflow = "hidden" : X().style.overflow = "";
                var e = d ? X().clientWidth + "px" : c.width, f = d ? X().clientHeight + "px" : c.height;
                if (F && N < 8 || da)f = o(l(f) + 2);
                var k = c.container = i(c.layout);
                d ? i(document.body).append(k) : c.srcElement.before(k);
                var h = i(".toolbar", k), n = i(".edit", k), m = c.statusbar = i(".statusbar", k);
                k.removeClass("container").addClass("ke-container ke-container-" +
                    c.themeType).css("width", e);
                if (d) {
                    k.css({position: "absolute", left: 0, top: 0, "z-index": 811211});
                    if (!la)c._scrollPos = na();
                    b.scrollTo(0, 0);
                    i(document.body).css({height: "1px", overflow: "hidden"});
                    i(document.body.parentNode).css("overflow", "hidden");
                    c._fullscreenExecuted = !0
                } else c._fullscreenExecuted && (i(document.body).css({
                    height: "",
                    overflow: ""
                }), i(document.body.parentNode).css("overflow", "")), c._scrollPos && b.scrollTo(c._scrollPos.x, c._scrollPos.y);
                var s = [];
                i.each(c.items, function (a, b) {
                    b == "|" ? s.push('<span class="ke-inline-block ke-separator"></span>') :
                        b == "/" ? s.push('<div class="ke-hr"></div>') : (s.push('<span class="ke-outline" data-name="' + b + '" title="' + c.lang(b) + '" unselectable="on">'), s.push('<span class="ke-toolbar-icon ke-toolbar-icon-url ke-icon-' + b + '" unselectable="on"></span></span>'))
                });
                var h = c.toolbar = wb({
                    src: h, html: s.join(""), noDisableItems: c.noDisableItems, click: function (a, b) {
                        a.stop();
                        if (c.menu) {
                            var d = c.menu.name;
                            c.hideMenu();
                            if (d === b)return
                        }
                        c.clickToolbar(b)
                    }
                }), j = l(f) - h.div.height(), r = c.edit = ub({
                    height: j > 0 && l(f) > c.minHeight ? j : c.minHeight,
                    src: n,
                    srcElement: c.srcElement,
                    designMode: c.designMode,
                    themesPath: c.themesPath,
                    bodyClass: c.bodyClass,
                    cssPath: c.cssPath,
                    cssData: c.cssData,
                    beforeGetHtml: function (a) {
                        a = c.beforeGetHtml(a);
                        a = ha(Ia(a));
                        return H(a, c.filterMode ? c.htmlTags : null, c.urlType, c.wellFormatMode, c.indentChar)
                    },
                    beforeSetHtml: function (a) {
                        a = H(a, c.filterMode ? c.htmlTags : null, "", !1);
                        return c.beforeSetHtml(a)
                    },
                    afterSetHtml: function () {
                        c.edit = r = this;
                        c.afterSetHtml()
                    },
                    afterCreate: function () {
                        c.edit = r = this;
                        c.cmd = r.cmd;
                        c._docMousedownFn = function () {
                            c.menu &&
                            c.hideMenu()
                        };
                        i(r.doc, document).mousedown(c._docMousedownFn);
                        Sb.call(c);
                        Tb.call(c);
                        Ub.call(c);
                        Vb.call(c);
                        r.afterChange(function () {
                            r.designMode && (c.updateState(), c.addBookmark(), c.options.afterChange && c.options.afterChange.call(c))
                        });
                        r.textarea.keyup(function (a) {
                            !a.ctrlKey && !a.altKey && Ib[a.which] && c.options.afterChange && c.options.afterChange.call(c)
                        });
                        c.readonlyMode && c.readonly();
                        c.isCreated = !0;
                        if (c.initContent === "")c.initContent = c.html();
                        if (c._undoStack.length > 0) {
                            var a = c._undoStack.pop();
                            a.start &&
                            (c.html(a.html), r.cmd.range.moveToBookmark(a), c.select())
                        }
                        c.afterCreate();
                        c.options.afterCreate && c.options.afterCreate.call(c)
                    }
                });
                m.removeClass("statusbar").addClass("ke-statusbar").append('<span class="ke-inline-block ke-statusbar-center-icon"></span>').append('<span class="ke-inline-block ke-statusbar-right-icon"></span>');
                if (c._fullscreenResizeHandler)i(b).unbind("resize", c._fullscreenResizeHandler), c._fullscreenResizeHandler = null;
                a();
                d ? (c._fullscreenResizeHandler = function () {
                    c.isCreated && c.resize(X().clientWidth,
                        X().clientHeight, !1)
                }, i(b).bind("resize", c._fullscreenResizeHandler), h.select("fullscreen"), m.first().css("visibility", "hidden"), m.last().css("visibility", "hidden")) : (la && i(b).bind("scroll", function () {
                    c._scrollPos = na()
                }), c.resizeType > 0 ? Xa({
                    moveEl: k, clickEl: m, moveFn: function (a, b, d, g, e, f) {
                        g += f;
                        c.resize(null, g)
                    }
                }) : m.first().css("visibility", "hidden"), c.resizeType === 2 ? Xa({
                    moveEl: k, clickEl: m.last(), moveFn: function (a, b, d, g, e, f) {
                        d += e;
                        g += f;
                        c.resize(d, g)
                    }
                }) : m.last().css("visibility", "hidden"));
                return c
            }, remove: function () {
                var a =
                    this;
                if (!a.isCreated)return a;
                a.beforeRemove();
                a.srcElement.data("kindeditor", "");
                a.menu && a.hideMenu();
                h(a.dialogs, function () {
                    a.hideDialog()
                });
                i(document).unbind("mousedown", a._docMousedownFn);
                a.toolbar.remove();
                a.edit.remove();
                a.statusbar.last().unbind();
                a.statusbar.unbind();
                a.container.remove();
                a.container = a.toolbar = a.edit = a.menu = null;
                a.dialogs = [];
                a.isCreated = !1;
                return a
            }, resize: function (a, c, b) {
                b = q(b, !0);
                if (a && (/%/.test(a) || (a = l(a), a = a < this.minWidth ? this.minWidth : a), this.container.css("width", o(a)),
                        b))this.width = o(a);
                if (c && (c = l(c), editHeight = l(c) - this.toolbar.div.height() - this.statusbar.height(), editHeight = editHeight < this.minHeight ? this.minHeight : editHeight, this.edit.setHeight(editHeight), b))this.height = o(c);
                return this
            }, select: function () {
                this.isCreated && this.cmd.select();
                return this
            }, html: function (a) {
                if (a === d)return this.isCreated ? this.edit.html() : wa(this.srcElement);
                this.isCreated ? this.edit.html(a) : wa(this.srcElement, a);
                this.isCreated && this.cmd.selection();
                return this
            }, fullHtml: function () {
                return this.isCreated ?
                    this.edit.html(d, !0) : ""
            }, text: function (a) {
                return a === d ? m(this.html().replace(/<(?!img|embed).*?>/ig, "").replace(/&nbsp;/ig, " ")) : this.html(s(a))
            }, isEmpty: function () {
                return m(this.text().replace(/\r\n|\n|\r/, "")) === ""
            }, isDirty: function () {
                return m(this.initContent.replace(/\r\n|\n|\r|t/g, "")) !== m(this.html().replace(/\r\n|\n|\r|t/g, ""))
            }, selectedHtml: function () {
                var a = this.isCreated ? this.cmd.range.html() : "";
                return a = ha(Ia(a))
            }, count: function (a) {
                a = (a || "html").toLowerCase();
                if (a === "html")return this.html().length;
                if (a === "text")return this.text().replace(/<(?:img|embed).*?>/ig, "K").replace(/\r\n|\n|\r/g, "").length;
                return 0
            }, exec: function (a) {
                var a = a.toLowerCase(), c = this.cmd, b = e(a, "selectall,copy,paste,print".split(",")) < 0;
                b && this.addBookmark(!1);
                c[a].apply(c, D(arguments, 1));
                b && (this.updateState(), this.addBookmark(!1), this.options.afterChange && this.options.afterChange.call(this));
                return this
            }, insertHtml: function (a, c) {
                if (!this.isCreated)return this;
                a = this.beforeSetHtml(a);
                this.exec("inserthtml", a, c);
                return this
            },
            appendHtml: function (a) {
                this.html(this.html() + a);
                if (this.isCreated)a = this.cmd, a.range.selectNodeContents(a.doc.body).collapse(!1), a.select();
                return this
            }, sync: function () {
                wa(this.srcElement, this.html());
                return this
            }, focus: function () {
                this.isCreated ? this.edit.focus() : this.srcElement[0].focus();
                return this
            }, blur: function () {
                this.isCreated ? this.edit.blur() : this.srcElement[0].blur();
                return this
            }, addBookmark: function (a) {
                var a = q(a, !0), c = this.edit, b = c.doc.body, d = Ia(b.innerHTML);
                if (a && this._undoStack.length >
                    0 && Math.abs(d.length - ha(this._undoStack[this._undoStack.length - 1].html).length) < this.minChangeSize)return this;
                c.designMode && !this._firstAddBookmark ? (c = this.cmd.range, a = c.createBookmark(!0), a.html = Ia(b.innerHTML), c.moveToBookmark(a)) : a = {html: d};
                this._firstAddBookmark = !1;
                Db(this._undoStack, a);
                return this
            }, undo: function () {
                return Eb.call(this, this._undoStack, this._redoStack)
            }, redo: function () {
                return Eb.call(this, this._redoStack, this._undoStack)
            }, fullscreen: function (a) {
                this.fullscreenMode = a === d ? !this.fullscreenMode :
                    a;
                this.addBookmark(!1);
                return this.remove().create()
            }, readonly: function (a) {
                var a = q(a, !0), c = this, b = c.edit, d = b.doc;
                c.designMode ? c.toolbar.disableAll(a, []) : h(c.noDisableItems, function () {
                    c.toolbar[a ? "disable" : "enable"](this)
                });
                F ? d.body.contentEditable = !a : d.designMode = a ? "off" : "on";
                b.textarea[0].disabled = a
            }, createMenu: function (a) {
                var c = this.toolbar.get(a.name), b = c.pos();
                a.x = b.x;
                a.y = b.y + c.height();
                a.z = this.options.zIndex;
                a.shadowMode = q(a.shadowMode, this.shadowMode);
                a.selectedColor !== d ? (a.cls = "ke-colorpicker-" +
                    this.themeType, a.noColor = this.lang("noColor"), this.menu = xb(a)) : (a.cls = "ke-menu-" + this.themeType, a.centerLineMode = !1, this.menu = ab(a));
                return this.menu
            }, hideMenu: function () {
                this.menu.remove();
                this.menu = null;
                return this
            }, hideContextmenu: function () {
                this.contextmenu.remove();
                this.contextmenu = null;
                return this
            }, createDialog: function (a) {
                var b = this;
                a.z = b.options.zIndex;
                a.shadowMode = q(a.shadowMode, b.shadowMode);
                a.closeBtn = q(a.closeBtn, {
                    name: b.lang("close"), click: function () {
                        b.hideDialog();
                        F && b.cmd && b.cmd.select()
                    }
                });
                a.noBtn = q(a.noBtn, {
                    name: b.lang(a.yesBtn ? "no" : "close"), click: function () {
                        b.hideDialog();
                        F && b.cmd && b.cmd.select()
                    }
                });
                if (b.dialogAlignType != "page")a.alignEl = b.container;
                a.cls = "ke-dialog-" + b.themeType;
                if (b.dialogs.length > 0) {
                    var d = b.dialogs[b.dialogs.length - 1];
                    b.dialogs[0].setMaskIndex(d.z + 2);
                    a.z = d.z + 3;
                    a.showMask = !1
                }
                a = yb(a);
                b.dialogs.push(a);
                return a
            }, hideDialog: function () {
                this.dialogs.length > 0 && this.dialogs.pop().remove();
                this.dialogs.length > 0 && this.dialogs[0].setMaskIndex(this.dialogs[this.dialogs.length -
                    1].z - 1);
                return this
            }, errorDialog: function (a) {
                var b = this.createDialog({
                    width: 750,
                    title: this.lang("uploadError"),
                    body: '<div style="padding:10px 20px;"><iframe frameborder="0" style="width:708px;height:400px;"></iframe></div>'
                }), b = i("iframe", b.div), d = i.iframeDoc(b);
                d.open();
                d.write(a);
                d.close();
                i(d.body).css("background-color", "#FFF");
                b[0].contentWindow.focus();
                return this
            }
        };
        _instances = [];
        i.remove = function (a) {
            Ca(a, function (a) {
                this.remove();
                _instances.splice(a, 1)
            })
        };
        i.sync = function (a) {
            Ca(a, function () {
                this.sync()
            })
        };
        i.html = function (a, b) {
            Ca(a, function () {
                this.html(b)
            })
        };
        i.insertHtml = function (a, b) {
            Ca(a, function () {
                this.insertHtml(b)
            })
        };
        i.appendHtml = function (a, b) {
            Ca(a, function () {
                this.appendHtml(b)
            })
        };
        F && N < 7 && ea(document, "BackgroundImageCache", !0);
        i.EditorClass = Ba;
        i.editor = function (a) {
            return new Ba(a)
        };
        i.create = Fb;
        i.instances = _instances;
        i.plugin = Ab;
        i.lang = Cb;
        Ab("core", function (a) {
            var c = this, g = {undo: "Z", redo: "Y", bold: "B", italic: "I", underline: "U", print: "P", selectall: "A"};
            c.afterSetHtml(function () {
                c.options.afterChange &&
                c.options.afterChange.call(c)
            });
            c.afterCreate(function () {
                if (c.syncType == "form") {
                    for (var d = a(c.srcElement), g = !1; d = d.parent();)if (d.name == "form") {
                        g = !0;
                        break
                    }
                    if (g) {
                        d.bind("submit", function () {
                            c.sync();
                            a(b).bind("unload", function () {
                                c.edit.textarea.remove()
                            })
                        });
                        var e = a('[type="reset"]', d);
                        e.click(function () {
                            c.html(c.initContent);
                            c.cmd.selection()
                        });
                        c.beforeRemove(function () {
                            d.unbind();
                            e.unbind()
                        })
                    }
                }
            });
            c.clickToolbar("source", function () {
                c.edit.designMode ? (c.toolbar.disableAll(!0), c.edit.design(!1), c.toolbar.select("source")) :
                    (c.toolbar.disableAll(!1), c.edit.design(!0), c.toolbar.unselect("source"), la ? setTimeout(function () {
                        c.cmd.selection()
                    }, 0) : c.cmd.selection());
                c.designMode = c.edit.designMode
            });
            c.afterCreate(function () {
                c.designMode || c.toolbar.disableAll(!0).select("source")
            });
            c.clickToolbar("fullscreen", function () {
                c.fullscreen()
            });
            if (c.fullscreenShortcut) {
                var f = !1;
                c.afterCreate(function () {
                    a(c.edit.doc, c.edit.textarea).keyup(function (a) {
                        a.which == 27 && setTimeout(function () {
                            c.fullscreen()
                        }, 0)
                    });
                    if (f) {
                        if (F && !c.designMode)return;
                        c.focus()
                    }
                    f || (f = !0)
                })
            }
            h("undo,redo".split(","), function (a, b) {
                g[b] && c.afterCreate(function () {
                    $(this.edit.doc, g[b], function () {
                        c.clickToolbar(b)
                    })
                });
                c.clickToolbar(b, function () {
                    c[b]()
                })
            });
            c.clickToolbar("formatblock", function () {
                var a = c.lang("formatblock.formatBlock"), b = {h1: 28, h2: 24, h3: 18, H4: 14, p: 12}, d = c.cmd.val("formatblock"), g = c.createMenu({
                    name: "formatblock",
                    width: c.langType == "en" ? 200 : 150
                });
                h(a, function (a, e) {
                    var f = "font-size:" + b[a] + "px;";
                    a.charAt(0) === "h" && (f += "font-weight:bold;");
                    g.addItem({
                        title: '<span style="' +
                        f + '" unselectable="on">' + e + "</span>", height: b[a] + 12, checked: d === a || d === e, click: function () {
                            c.select().exec("formatblock", "<" + a + ">").hideMenu()
                        }
                    })
                })
            });
            c.clickToolbar("fontname", function () {
                var a = c.cmd.val("fontname"), b = c.createMenu({name: "fontname", width: 150});
                h(c.lang("fontname.fontName"), function (d, g) {
                    b.addItem({
                        title: '<span style="font-family: ' + d + ';" unselectable="on">' + g + "</span>",
                        checked: a === d.toLowerCase() || a === g.toLowerCase(),
                        click: function () {
                            c.exec("fontname", d).hideMenu()
                        }
                    })
                })
            });
            c.clickToolbar("fontsize",
                function () {
                    var a = c.cmd.val("fontsize"), b = c.createMenu({name: "fontsize", width: 150});
                    h(c.fontSizeTable, function (d, g) {
                        b.addItem({
                            title: '<span style="font-size:' + g + ';" unselectable="on">' + g + "</span>", height: l(g) + 12, checked: a === g, click: function () {
                                c.exec("fontsize", g).hideMenu()
                            }
                        })
                    })
                });
            h("forecolor,hilitecolor".split(","), function (a, b) {
                c.clickToolbar(b, function () {
                    c.createMenu({
                        name: b, selectedColor: c.cmd.val(b) || "default", colors: c.colorTable, click: function (a) {
                            c.exec(b, a).hideMenu()
                        }
                    })
                })
            });
            h("cut,copy,paste".split(","),
                function (a, b) {
                    c.clickToolbar(b, function () {
                        c.focus();
                        try {
                            c.exec(b, null)
                        } catch (a) {
                            alert(c.lang(b + "Error"))
                        }
                    })
                });
            c.clickToolbar("about", function () {
                var a = '<div style="margin:20px;"><div>KindEditor ' + Ka + '</div><div>Copyright &copy; <a href="http://www.kindsoft.net/" target="_blank">kindsoft.net</a> All rights reserved.</div></div>';
                c.createDialog({name: "about", width: 350, title: c.lang("about"), body: a})
            });
            c.plugin.getSelectedLink = function () {
                return c.cmd.commonAncestor("a")
            };
            c.plugin.getSelectedImage = function () {
                return Ha(c.edit.cmd.range,
                    function (a) {
                        return !/^ke-\w+$/i.test(a[0].className)
                    })
            };
            c.plugin.getSelectedFlash = function () {
                return Ha(c.edit.cmd.range, function (a) {
                    return a[0].className == "ke-flash"
                })
            };
            c.plugin.getSelectedMedia = function () {
                return Ha(c.edit.cmd.range, function (a) {
                    return a[0].className == "ke-media" || a[0].className == "ke-rm"
                })
            };
            c.plugin.getSelectedAnchor = function () {
                return Ha(c.edit.cmd.range, function (a) {
                    return a[0].className == "ke-anchor"
                })
            };
            h("link,image,flash,media,anchor".split(","), function (a, b) {
                var g = b.charAt(0).toUpperCase() +
                    b.substr(1);
                h("edit,delete".split(","), function (a, e) {
                    c.addContextmenu({
                        title: c.lang(e + g), click: function () {
                            c.loadPlugin(b, function () {
                                c.plugin[b][e]();
                                c.hideMenu()
                            })
                        }, cond: c.plugin["getSelected" + g], width: 150, iconClass: e == "edit" ? "ke-icon-" + b : d
                    })
                });
                c.addContextmenu({title: "-"})
            });
            c.plugin.getSelectedTable = function () {
                return c.cmd.commonAncestor("table")
            };
            c.plugin.getSelectedRow = function () {
                return c.cmd.commonAncestor("tr")
            };
            c.plugin.getSelectedCell = function () {
                return c.cmd.commonAncestor("td")
            };
            h("prop,cellprop,colinsertleft,colinsertright,rowinsertabove,rowinsertbelow,rowmerge,colmerge,rowsplit,colsplit,coldelete,rowdelete,insert,delete".split(","),
                function (a, b) {
                    var d = e(b, ["prop", "delete"]) < 0 ? c.plugin.getSelectedCell : c.plugin.getSelectedTable;
                    c.addContextmenu({
                        title: c.lang("table" + b), click: function () {
                            c.loadPlugin("table", function () {
                                c.plugin.table[b]();
                                c.hideMenu()
                            })
                        }, cond: d, width: 170, iconClass: "ke-icon-table" + b
                    })
                });
            c.addContextmenu({title: "-"});
            h("selectall,justifyleft,justifycenter,justifyright,justifyfull,insertorderedlist,insertunorderedlist,indent,outdent,subscript,superscript,hr,print,bold,italic,underline,strikethrough,removeformat,unlink".split(","),
                function (a, b) {
                    g[b] && c.afterCreate(function () {
                        $(this.edit.doc, g[b], function () {
                            c.cmd.selection();
                            c.clickToolbar(b)
                        })
                    });
                    c.clickToolbar(b, function () {
                        c.focus().exec(b, null)
                    })
                });
            c.afterCreate(function () {
                function b() {
                    g.range.moveToBookmark(e);
                    g.select();
                    ka && (a("div." + i, f).each(function () {
                        a(this).after("<br />").remove(!0)
                    }), a("span.Apple-style-span", f).remove(!0), a("span.Apple-tab-span", f).remove(!0), a("span[style]", f).each(function () {
                        a(this).css("white-space") == "nowrap" && a(this).remove(!0)
                    }), a("meta", f).remove());
                    var d = f[0].innerHTML;
                    f.remove();
                    d !== "" && (ka && (d = d.replace(/(<br>)\1/ig, "$1")), c.pasteType === 2 && (d = d.replace(/(<(?:p|p\s[^>]*)>) *(<\/p>)/ig, ""), /schemas-microsoft-com|worddocument|mso-\w+/i.test(d) ? d = U(d, c.filterMode ? c.htmlTags : a.options.htmlTags) : (d = H(d, c.filterMode ? c.htmlTags : null), d = c.beforeSetHtml(d))), c.pasteType === 1 && (d = d.replace(/&nbsp;/ig, " "), d = d.replace(/\n\s*\n/g, "\n"), d = d.replace(/<br[^>]*>/ig, "\n"), d = d.replace(/<\/p><p[^>]*>/ig, "\n"), d = d.replace(/<[^>]+>/g, ""), d = d.replace(/ {2}/g, " &nbsp;"),
                        c.newlineTag == "p" ? /\n/.test(d) && (d = d.replace(/^/, "<p>").replace(/$/, "<br /></p>").replace(/\n/g, "<br /></p><p>")) : d = d.replace(/\n/g, "<br />$&")), c.insertHtml(d, !0))
                }

                var d = c.edit.doc, g, e, f, i = "__kindeditor_paste__", h = !1;
                a(d.body).bind("paste", function (l) {
                    if (c.pasteType === 0)l.stop(); else if (!h) {
                        h = !0;
                        a("div." + i, d).remove();
                        g = c.cmd.selection();
                        e = g.range.createBookmark();
                        f = a('<div class="' + i + '"></div>', d).css({
                            position: "absolute", width: "1px", height: "1px", overflow: "hidden", left: "-1981px", top: a(e.start).pos().y +
                            "px", "white-space": "nowrap"
                        });
                        a(d.body).append(f);
                        if (F) {
                            var n = g.range.get(!0);
                            n.moveToElementText(f[0]);
                            n.select();
                            n.execCommand("paste");
                            l.preventDefault()
                        } else g.range.selectNodeContents(f[0]), g.select();
                        setTimeout(function () {
                            b();
                            h = !1
                        }, 0)
                    }
                })
            });
            c.beforeGetHtml(function (a) {
                F && N <= 8 && (a = a.replace(/<div\s+[^>]*data-ke-input-tag="([^"]*)"[^>]*>([\s\S]*?)<\/div>/ig, function (a, b) {
                    return unescape(b)
                }), a = a.replace(/(<input)((?:\s+[^>]*)?>)/ig, function (a, b, c) {
                    if (!/\s+type="[^"]+"/i.test(a))return b + ' type="text"' +
                        c;
                    return a
                }));
                return a.replace(/(<(?:noscript|noscript\s[^>]*)>)([\s\S]*?)(<\/noscript>)/ig, function (a, b, c, d) {
                    return b + v(c).replace(/\s+/g, " ") + d
                }).replace(/<img[^>]*class="?ke-(flash|rm|media)"?[^>]*>/ig, function (a) {
                    var a = K(a), b = M(a.style || ""), c = S(a["data-ke-tag"]), d = q(b.width, ""), b = q(b.height, "");
                    /px/i.test(d) && (d = l(d));
                    /px/i.test(b) && (b = l(b));
                    c.width = q(a.width, d);
                    c.height = q(a.height, b);
                    return Na(c)
                }).replace(/<img[^>]*class="?ke-anchor"?[^>]*>/ig, function (a) {
                    a = K(a);
                    return '<a name="' + unescape(a["data-ke-name"]) +
                        '"></a>'
                }).replace(/<div\s+[^>]*data-ke-script-attr="([^"]*)"[^>]*>([\s\S]*?)<\/div>/ig, function (a, b, c) {
                    return "<script" + unescape(b) + ">" + unescape(c) + "<\/script>"
                }).replace(/<div\s+[^>]*data-ke-noscript-attr="([^"]*)"[^>]*>([\s\S]*?)<\/div>/ig, function (a, b, c) {
                    return "<noscript" + unescape(b) + ">" + unescape(c) + "</noscript>"
                }).replace(/(<[^>]*)data-ke-src="([^"]*)"([^>]*>)/ig, function (a, b, c) {
                    a = a.replace(/(\s+(?:href|src)=")[^"]*(")/i, function (a, b, d) {
                        return b + v(c) + d
                    });
                    return a = a.replace(/\s+data-ke-src="[^"]*"/i,
                        "")
                }).replace(/(<[^>]+\s)data-ke-(on\w+="[^"]*"[^>]*>)/ig, function (a, b, c) {
                    return b + c
                })
            });
            c.beforeSetHtml(function (a) {
                F && N <= 8 && (a = a.replace(/<input[^>]*>|<(select|button)[^>]*>[\s\S]*?<\/\1>/ig, function (a) {
                    var b = K(a);
                    if (M(b.style || "").display == "none")return '<div class="ke-display-none" data-ke-input-tag="' + escape(a) + '"></div>';
                    return a
                }));
                return a.replace(/<embed[^>]*type="([^"]+)"[^>]*>(?:<\/embed>)?/ig, function (a) {
                    a = K(a);
                    a.src = q(a.src, "");
                    a.width = q(a.width, 0);
                    a.height = q(a.height, 0);
                    return kb(c.themesPath +
                        "common/blank.gif", a)
                }).replace(/<a[^>]*name="([^"]+)"[^>]*>(?:<\/a>)?/ig, function (a) {
                    var b = K(a);
                    if (b.href !== d)return a;
                    return '<img class="ke-anchor" src="' + c.themesPath + 'common/anchor.gif" data-ke-name="' + escape(b.name) + '" />'
                }).replace(/<script([^>]*)>([\s\S]*?)<\/script>/ig, function (a, b, c) {
                    return '<div class="ke-script" data-ke-script-attr="' + escape(b) + '">' + escape(c) + "</div>"
                }).replace(/<noscript([^>]*)>([\s\S]*?)<\/noscript>/ig, function (a, b, c) {
                    return '<div class="ke-noscript" data-ke-noscript-attr="' +
                        escape(b) + '">' + escape(c) + "</div>"
                }).replace(/(<[^>]*)(href|src)="([^"]*)"([^>]*>)/ig, function (a, b, c, d, g) {
                    if (a.match(/\sdata-ke-src="[^"]*"/i))return a;
                    return a = b + c + '="' + d + '" data-ke-src="' + s(d) + '"' + g
                }).replace(/(<[^>]+\s)(on\w+="[^"]*"[^>]*>)/ig, function (a, b, c) {
                    return b + "data-ke-" + c
                }).replace(/<table[^>]*\s+border="0"[^>]*>/ig, function (a) {
                    if (a.indexOf("ke-zeroborder") >= 0)return a;
                    return O(a, "ke-zeroborder")
                })
            })
        })
    }
})(window);
KindEditor.lang({
    source: "HTML\u4ee3\u7801",
    preview: "\u9884\u89c8",
    undo: "\u540e\u9000(Ctrl+Z)",
    redo: "\u524d\u8fdb(Ctrl+Y)",
    cut: "\u526a\u5207(Ctrl+X)",
    copy: "\u590d\u5236(Ctrl+C)",
    paste: "\u7c98\u8d34(Ctrl+V)",
    plainpaste: "\u7c98\u8d34\u4e3a\u65e0\u683c\u5f0f\u6587\u672c",
    wordpaste: "\u4eceWord\u7c98\u8d34",
    selectall: "\u5168\u9009(Ctrl+A)",
    justifyleft: "\u5de6\u5bf9\u9f50",
    justifycenter: "\u5c45\u4e2d",
    justifyright: "\u53f3\u5bf9\u9f50",
    justifyfull: "\u4e24\u7aef\u5bf9\u9f50",
    insertorderedlist: "\u7f16\u53f7",
    insertunorderedlist: "\u9879\u76ee\u7b26\u53f7",
    indent: "\u589e\u52a0\u7f29\u8fdb",
    outdent: "\u51cf\u5c11\u7f29\u8fdb",
    subscript: "\u4e0b\u6807",
    superscript: "\u4e0a\u6807",
    formatblock: "\u6bb5\u843d",
    fontname: "\u5b57\u4f53",
    fontsize: "\u6587\u5b57\u5927\u5c0f",
    forecolor: "\u6587\u5b57\u989c\u8272",
    hilitecolor: "\u6587\u5b57\u80cc\u666f",
    bold: "\u7c97\u4f53(Ctrl+B)",
    italic: "\u659c\u4f53(Ctrl+I)",
    underline: "\u4e0b\u5212\u7ebf(Ctrl+U)",
    strikethrough: "\u5220\u9664\u7ebf",
    removeformat: "\u5220\u9664\u683c\u5f0f",
    image: "\u56fe\u7247",
    multiimage: "\u6279\u91cf\u56fe\u7247\u4e0a\u4f20",
    flash: "Flash",
    media: "\u89c6\u97f3\u9891",
    table: "\u8868\u683c",
    tablecell: "\u5355\u5143\u683c",
    hr: "\u63d2\u5165\u6a2a\u7ebf",
    emoticons: "\u63d2\u5165\u8868\u60c5",
    link: "\u8d85\u7ea7\u94fe\u63a5",
    unlink: "\u53d6\u6d88\u8d85\u7ea7\u94fe\u63a5",
    fullscreen: "\u5168\u5c4f\u663e\u793a",
    about: "\u5173\u4e8e",
    print: "\u6253\u5370(Ctrl+P)",
    filemanager: "\u6587\u4ef6\u7a7a\u95f4",
    code: "\u63d2\u5165\u7a0b\u5e8f\u4ee3\u7801",
    map: "Google\u5730\u56fe",
    baidumap: "\u767e\u5ea6\u5730\u56fe",
    lineheight: "\u884c\u8ddd",
    clearhtml: "\u6e05\u7406HTML\u4ee3\u7801",
    pagebreak: "\u63d2\u5165\u5206\u9875\u7b26",
    quickformat: "\u4e00\u952e\u6392\u7248",
    insertfile: "\u63d2\u5165\u6587\u4ef6",
    template: "\u63d2\u5165\u6a21\u677f",
    anchor: "\u951a\u70b9",
    yes: "\u786e\u5b9a",
    no: "\u53d6\u6d88",
    close: "\u5173\u95ed",
    editImage: "\u56fe\u7247\u5c5e\u6027",
    deleteImage: "\u5220\u9664\u56fe\u7247",
    editFlash: "Flash\u5c5e\u6027",
    deleteFlash: "\u5220\u9664Flash",
    editMedia: "\u89c6\u97f3\u9891\u5c5e\u6027",
    deleteMedia: "\u5220\u9664\u89c6\u97f3\u9891",
    editLink: "\u8d85\u7ea7\u94fe\u63a5\u5c5e\u6027",
    deleteLink: "\u53d6\u6d88\u8d85\u7ea7\u94fe\u63a5",
    editAnchor: "\u951a\u70b9\u5c5e\u6027",
    deleteAnchor: "\u5220\u9664\u951a\u70b9",
    tableprop: "\u8868\u683c\u5c5e\u6027",
    tablecellprop: "\u5355\u5143\u683c\u5c5e\u6027",
    tableinsert: "\u63d2\u5165\u8868\u683c",
    tabledelete: "\u5220\u9664\u8868\u683c",
    tablecolinsertleft: "\u5de6\u4fa7\u63d2\u5165\u5217",
    tablecolinsertright: "\u53f3\u4fa7\u63d2\u5165\u5217",
    tablerowinsertabove: "\u4e0a\u65b9\u63d2\u5165\u884c",
    tablerowinsertbelow: "\u4e0b\u65b9\u63d2\u5165\u884c",
    tablerowmerge: "\u5411\u4e0b\u5408\u5e76\u5355\u5143\u683c",
    tablecolmerge: "\u5411\u53f3\u5408\u5e76\u5355\u5143\u683c",
    tablerowsplit: "\u62c6\u5206\u884c",
    tablecolsplit: "\u62c6\u5206\u5217",
    tablecoldelete: "\u5220\u9664\u5217",
    tablerowdelete: "\u5220\u9664\u884c",
    noColor: "\u65e0\u989c\u8272",
    pleaseSelectFile: "\u8bf7\u9009\u62e9\u6587\u4ef6\u3002",
    invalidImg: "\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002\n\u53ea\u5141\u8bb8jpg,gif,bmp,png\u683c\u5f0f\u3002",
    invalidMedia: "\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002\n\u53ea\u5141\u8bb8swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb\u683c\u5f0f\u3002",
    invalidWidth: "\u5bbd\u5ea6\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
    invalidHeight: "\u9ad8\u5ea6\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
    invalidBorder: "\u8fb9\u6846\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
    invalidUrl: "\u8bf7\u8f93\u5165\u6709\u6548\u7684URL\u5730\u5740\u3002",
    invalidRows: "\u884c\u6570\u4e3a\u5fc5\u9009\u9879\uff0c\u53ea\u5141\u8bb8\u8f93\u5165\u5927\u4e8e0\u7684\u6570\u5b57\u3002",
    invalidCols: "\u5217\u6570\u4e3a\u5fc5\u9009\u9879\uff0c\u53ea\u5141\u8bb8\u8f93\u5165\u5927\u4e8e0\u7684\u6570\u5b57\u3002",
    invalidPadding: "\u8fb9\u8ddd\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
    invalidSpacing: "\u95f4\u8ddd\u5fc5\u987b\u4e3a\u6570\u5b57\u3002",
    invalidJson: "\u670d\u52a1\u5668\u53d1\u751f\u6545\u969c\u3002",
    uploadSuccess: "\u4e0a\u4f20\u6210\u529f\u3002",
    cutError: "\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u526a\u5207\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+X)\u6765\u5b8c\u6210\u3002",
    copyError: "\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u590d\u5236\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+C)\u6765\u5b8c\u6210\u3002",
    pasteError: "\u60a8\u7684\u6d4f\u89c8\u5668\u5b89\u5168\u8bbe\u7f6e\u4e0d\u5141\u8bb8\u4f7f\u7528\u7c98\u8d34\u64cd\u4f5c\uff0c\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u6765\u5b8c\u6210\u3002",
    ajaxLoading: "\u52a0\u8f7d\u4e2d\uff0c\u8bf7\u7a0d\u5019 ...",
    uploadLoading: "\u4e0a\u4f20\u4e2d\uff0c\u8bf7\u7a0d\u5019 ...",
    uploadError: "\u4e0a\u4f20\u9519\u8bef",
    "plainpaste.comment": "\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u628a\u5185\u5bb9\u7c98\u8d34\u5230\u4e0b\u9762\u7684\u65b9\u6846\u91cc\u3002",
    "wordpaste.comment": "\u8bf7\u4f7f\u7528\u5feb\u6377\u952e(Ctrl+V)\u628a\u5185\u5bb9\u7c98\u8d34\u5230\u4e0b\u9762\u7684\u65b9\u6846\u91cc\u3002",
    "code.pleaseInput": "\u8bf7\u8f93\u5165\u7a0b\u5e8f\u4ee3\u7801\u3002",
    "link.url": "URL",
    "link.linkType": "\u6253\u5f00\u7c7b\u578b",
    "link.newWindow": "\u65b0\u7a97\u53e3",
    "link.selfWindow": "\u5f53\u524d\u7a97\u53e3",
    "flash.url": "URL",
    "flash.width": "\u5bbd\u5ea6",
    "flash.height": "\u9ad8\u5ea6",
    "flash.upload": "\u4e0a\u4f20",
    "flash.viewServer": "\u6587\u4ef6\u7a7a\u95f4",
    "media.url": "URL",
    "media.width": "\u5bbd\u5ea6",
    "media.height": "\u9ad8\u5ea6",
    "media.autostart": "\u81ea\u52a8\u64ad\u653e",
    "media.upload": "\u4e0a\u4f20",
    "media.viewServer": "\u6587\u4ef6\u7a7a\u95f4",
    "image.remoteImage": "\u7f51\u7edc\u56fe\u7247",
    "image.localImage": "\u672c\u5730\u4e0a\u4f20",
    "image.remoteUrl": "\u56fe\u7247\u5730\u5740",
    "image.localUrl": "\u4e0a\u4f20\u6587\u4ef6",
    "image.size": "\u56fe\u7247\u5927\u5c0f",
    "image.width": "\u5bbd",
    "image.height": "\u9ad8",
    "image.resetSize": "\u91cd\u7f6e\u5927\u5c0f",
    "image.align": "\u5bf9\u9f50\u65b9\u5f0f",
    "image.defaultAlign": "\u9ed8\u8ba4\u65b9\u5f0f",
    "image.leftAlign": "\u5de6\u5bf9\u9f50",
    "image.rightAlign": "\u53f3\u5bf9\u9f50",
    "image.imgTitle": "\u56fe\u7247\u8bf4\u660e",
    "image.upload": "\u6d4f\u89c8...",
    "image.viewServer": "\u56fe\u7247\u7a7a\u95f4",
    "multiimage.uploadDesc": "\u5141\u8bb8\u7528\u6237\u540c\u65f6\u4e0a\u4f20<%=uploadLimit%>\u5f20\u56fe\u7247\uff0c\u5355\u5f20\u56fe\u7247\u5bb9\u91cf\u4e0d\u8d85\u8fc7<%=sizeLimit%>",
    "multiimage.startUpload": "\u5f00\u59cb\u4e0a\u4f20",
    "multiimage.clearAll": "\u5168\u90e8\u6e05\u7a7a",
    "multiimage.insertAll": "\u5168\u90e8\u63d2\u5165",
    "multiimage.queueLimitExceeded": "\u6587\u4ef6\u6570\u91cf\u8d85\u8fc7\u9650\u5236\u3002",
    "multiimage.fileExceedsSizeLimit": "\u6587\u4ef6\u5927\u5c0f\u8d85\u8fc7\u9650\u5236\u3002",
    "multiimage.zeroByteFile": "\u65e0\u6cd5\u4e0a\u4f20\u7a7a\u6587\u4ef6\u3002",
    "multiimage.invalidFiletype": "\u6587\u4ef6\u7c7b\u578b\u4e0d\u6b63\u786e\u3002",
    "multiimage.unknownError": "\u53d1\u751f\u5f02\u5e38\uff0c\u65e0\u6cd5\u4e0a\u4f20\u3002",
    "multiimage.pending": "\u7b49\u5f85\u4e0a\u4f20",
    "multiimage.uploadError": "\u4e0a\u4f20\u5931\u8d25",
    "filemanager.emptyFolder": "\u7a7a\u6587\u4ef6\u5939",
    "filemanager.moveup": "\u79fb\u5230\u4e0a\u4e00\u7ea7\u6587\u4ef6\u5939",
    "filemanager.viewType": "\u663e\u793a\u65b9\u5f0f\uff1a",
    "filemanager.viewImage": "\u7f29\u7565\u56fe",
    "filemanager.listImage": "\u8be6\u7ec6\u4fe1\u606f",
    "filemanager.orderType": "\u6392\u5e8f\u65b9\u5f0f\uff1a",
    "filemanager.fileName": "\u540d\u79f0",
    "filemanager.fileSize": "\u5927\u5c0f",
    "filemanager.fileType": "\u7c7b\u578b",
    "insertfile.url": "URL",
    "insertfile.title": "\u6587\u4ef6\u8bf4\u660e",
    "insertfile.upload": "\u4e0a\u4f20",
    "insertfile.viewServer": "\u6587\u4ef6\u7a7a\u95f4",
    "table.cells": "\u5355\u5143\u683c\u6570",
    "table.rows": "\u884c\u6570",
    "table.cols": "\u5217\u6570",
    "table.size": "\u5927\u5c0f",
    "table.width": "\u5bbd\u5ea6",
    "table.height": "\u9ad8\u5ea6",
    "table.percent": "%",
    "table.px": "px",
    "table.space": "\u8fb9\u8ddd\u95f4\u8ddd",
    "table.padding": "\u8fb9\u8ddd",
    "table.spacing": "\u95f4\u8ddd",
    "table.align": "\u5bf9\u9f50\u65b9\u5f0f",
    "table.textAlign": "\u6c34\u5e73\u5bf9\u9f50",
    "table.verticalAlign": "\u5782\u76f4\u5bf9\u9f50",
    "table.alignDefault": "\u9ed8\u8ba4",
    "table.alignLeft": "\u5de6\u5bf9\u9f50",
    "table.alignCenter": "\u5c45\u4e2d",
    "table.alignRight": "\u53f3\u5bf9\u9f50",
    "table.alignTop": "\u9876\u90e8",
    "table.alignMiddle": "\u4e2d\u90e8",
    "table.alignBottom": "\u5e95\u90e8",
    "table.alignBaseline": "\u57fa\u7ebf",
    "table.border": "\u8fb9\u6846",
    "table.borderWidth": "\u8fb9\u6846",
    "table.borderColor": "\u989c\u8272",
    "table.backgroundColor": "\u80cc\u666f\u989c\u8272",
    "map.address": "\u5730\u5740: ",
    "map.search": "\u641c\u7d22",
    "baidumap.address": "\u5730\u5740: ",
    "baidumap.search": "\u641c\u7d22",
    "baidumap.insertDynamicMap": "\u63d2\u5165\u52a8\u6001\u5730\u56fe",
    "anchor.name": "\u951a\u70b9\u540d\u79f0",
    "formatblock.formatBlock": {h1: "\u6807\u9898 1", h2: "\u6807\u9898 2", h3: "\u6807\u9898 3", h4: "\u6807\u9898 4", p: "\u6b63 \u6587"},
    "fontname.fontName": {
        SimSun: "\u5b8b\u4f53",
        NSimSun: "\u65b0\u5b8b\u4f53",
        FangSong_GB2312: "\u4eff\u5b8b_GB2312",
        KaiTi_GB2312: "\u6977\u4f53_GB2312",
        SimHei: "\u9ed1\u4f53",
        "Microsoft YaHei": "\u5fae\u8f6f\u96c5\u9ed1",
        Arial: "Arial",
        "Arial Black": "Arial Black",
        "Times New Roman": "Times New Roman",
        "Courier New": "Courier New",
        Tahoma: "Tahoma",
        Verdana: "Verdana"
    },
    "lineheight.lineHeight": [{1: "\u5355\u500d\u884c\u8ddd"}, {"1.5": "1.5\u500d\u884c\u8ddd"}, {2: "2\u500d\u884c\u8ddd"}, {"2.5": "2.5\u500d\u884c\u8ddd"}, {3: "3\u500d\u884c\u8ddd"}],
    "template.selectTemplate": "\u53ef\u9009\u6a21\u677f",
    "template.replaceContent": "\u66ff\u6362\u5f53\u524d\u5185\u5bb9",
    "template.fileList": {"1.html": "\u56fe\u7247\u548c\u6587\u5b57", "2.html": "\u8868\u683c", "3.html": "\u9879\u76ee\u7f16\u53f7"}
}, "zh_CN");
KindEditor.plugin("anchor", function (b) {
    var d = this, f = d.lang("anchor.");
    d.plugin.anchor = {
        edit: function () {
            var j = ['<div style="padding:20px;"><div class="ke-dialog-row">', '<label for="keName">' + f.name + "</label>", '<input class="ke-input-text" type="text" id="keName" name="name" value="" style="width:100px;" /></div></div>'].join(""), j = d.createDialog({
                    name: "anchor",
                    width: 300,
                    title: d.lang("anchor"),
                    body: j,
                    yesBtn: {
                        name: d.lang("yes"), click: function () {
                            d.insertHtml('<a name="' + e.val() + '">').hideDialog().focus()
                        }
                    }
                }).div,
                e = b('input[name="name"]', j);
            (j = d.plugin.getSelectedAnchor()) && e.val(unescape(j.attr("data-ke-name")));
            e[0].focus();
            e[0].select()
        }, "delete": function () {
            d.plugin.getSelectedAnchor().remove()
        }
    };
    d.clickToolbar("anchor", d.plugin.anchor.edit)
});
KindEditor.plugin("autoheight", function (b) {
    function d() {
        var d = j.edit, f = d.doc.body;
        d.iframe.height(e);
        j.resize(null, Math.max((b.IE ? f.scrollHeight : f.offsetHeight) + 76, e))
    }

    function f() {
        e = b.removeUnit(j.height);
        j.edit.afterChange(d);
        var f = j.edit, m = f.doc.body;
        f.iframe[0].scroll = "no";
        m.style.overflowY = "hidden";
        d()
    }

    var j = this;
    if (j.autoHeightMode) {
        var e;
        j.isCreated ? f() : j.afterCreate(f)
    }
});
KindEditor.plugin("baidumap", function (b) {
    var d = this, f = d.lang("baidumap."), j = b.undef(d.mapWidth, 558), e = b.undef(d.mapHeight, 360);
    d.clickToolbar("baidumap", function () {
        function h() {
            v = r[0].contentWindow;
            p = b.iframeDoc(r)
        }

        var m = ['<div style="padding:10px 20px;"><div class="ke-header"><div class="ke-left">', f.address + ' <input id="kindeditor_plugin_map_address" name="address" class="ke-input-text" value="" style="width:200px;" /> ', '<span class="ke-button-common ke-button-outer">', '<input type="button" name="searchBtn" class="ke-button-common ke-button" value="' +
        f.search + '" />', '</span></div><div class="ke-right">', '<input type="checkbox" id="keInsertDynamicMap" name="insertDynamicMap" value="1" /> <label for="keInsertDynamicMap">' + f.insertDynamicMap + "</label>", '</div><div class="ke-clearfix"></div></div>', '<div class="ke-map" style="width:' + j + "px;height:" + e + 'px;"></div>', "</div>"].join(""), m = d.createDialog({
            name: "baidumap", width: j + 42, title: d.lang("baidumap"), body: m, yesBtn: {
                name: d.lang("yes"), click: function () {
                    var b = v.map, f = b.getCenter(), f = f.lng + "," + f.lat,
                        b = b.getZoom(), b = [s[0].checked ? d.pluginsPath + "baidumap/index.html" : "http://api.map.baidu.com/staticimage", "?center=" + encodeURIComponent(f), "&zoom=" + encodeURIComponent(b), "&width=" + j, "&height=" + e, "&markers=" + encodeURIComponent(f), "&markerStyles=" + encodeURIComponent("l,A")].join("");
                    s[0].checked ? d.insertHtml('<iframe src="' + b + '" frameborder="0" style="width:' + (j + 2) + "px;height:" + (e + 2) + 'px;"></iframe>') : d.exec("insertimage", b);
                    d.hideDialog().focus()
                }
            }, beforeRemove: function () {
                l.remove();
                p && p.write("");
                r.remove()
            }
        }), n = m.div, o = b('[name="address"]', n), l = b('[name="searchBtn"]', n), s = b('[name="insertDynamicMap"]', m.div), v, p, r = b('<iframe class="ke-textarea" frameborder="0" src="' + d.pluginsPath + 'baidumap/map.html" style="width:' + j + "px;height:" + e + 'px;"></iframe>');
        r.bind("load", function () {
            r.unbind("load");
            b.IE ? h() : setTimeout(h, 0)
        });
        b(".ke-map", n).replaceWith(r);
        l.click(function () {
            v.search(o.val())
        })
    })
});
KindEditor.plugin("clearhtml", function (b) {
    var d = this;
    d.clickToolbar("clearhtml", function () {
        d.focus();
        var f = d.html(), f = f.replace(/(<script[^>]*>)([\s\S]*?)(<\/script>)/ig, ""), f = f.replace(/(<style[^>]*>)([\s\S]*?)(<\/style>)/ig, ""), f = b.formatHtml(f, {
            a: ["href", "target"],
            embed: ["src", "width", "height", "type", "loop", "autostart", "quality", ".width", ".height", "align", "allowscriptaccess"],
            img: ["src", "width", "height", "border", "alt", "title", ".width", ".height"],
            table: ["border"],
            "td,th": ["rowspan", "colspan"],
            "div,hr,br,tbody,tr,p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6": []
        });
        d.html(f);
        d.cmd.selection(!0);
        d.addBookmark()
    })
});
KindEditor.plugin("code", function (b) {
    var d = this;
    d.clickToolbar("code", function () {
        var f = d.lang("code."), j = d.createDialog({
            name: "code",
            width: 450,
            title: d.lang("code"),
            body: '<div style="padding:10px 20px;"><div class="ke-dialog-row"><select class="ke-code-type"><option value="js">JavaScript</option><option value="html">HTML</option><option value="css">CSS</option><option value="php">PHP</option><option value="pl">Perl</option><option value="py">Python</option><option value="rb">Ruby</option><option value="java">Java</option><option value="vb">ASP/VB</option><option value="cpp">C/C++</option><option value="cs">C#</option><option value="xml">XML</option><option value="bsh">Shell</option><option value="">Other</option></select></div><textarea class="ke-textarea" style="width:408px;height:260px;"></textarea></div>',
            yesBtn: {
                name: d.lang("yes"),
                click: function () {
                    var h = b(".ke-code-type", j.div).val(), m = e.val(), h = '<pre class="prettyprint' + (h === "" ? "" : " lang-" + h) + '">\n' + b.escape(m) + "</pre> ";
                    b.trim(m) === "" ? (alert(f.pleaseInput), e[0].focus()) : d.insertHtml(h).hideDialog().focus()
                }
            }
        }), e = b("textarea", j.div);
        e[0].focus()
    })
});
KindEditor.plugin("emoticons", function (b) {
    var d = this, f = d.emoticonsPath || d.pluginsPath + "emoticons/images/", j = d.allowPreviewEmoticons === void 0 ? !0 : d.allowPreviewEmoticons, e = 1;
    d.clickToolbar("emoticons", function () {
        function h(e, h, l) {
            B ? e.mouseover(function () {
                h > D ? (B.css("left", 0), B.css("right", "")) : (B.css("left", ""), B.css("right", 0));
                G.attr("src", f + l + ".gif");
                b(this).addClass("ke-on")
            }) : e.mouseover(function () {
                b(this).addClass("ke-on")
            });
            e.mouseout(function () {
                b(this).removeClass("ke-on")
            });
            e.click(function (b) {
                d.insertHtml('<img src="' +
                    f + l + '.gif" border="0" alt="" />').hideMenu().focus();
                b.stop()
            })
        }

        function m(d, e) {
            var l = document.createElement("table");
            e.append(l);
            B && (b(l).mouseover(function () {
                B.show("block")
            }), b(l).mouseout(function () {
                B.hide()
            }), A.push(b(l)));
            l.className = "ke-table";
            l.cellPadding = 0;
            l.cellSpacing = 0;
            l.border = 0;
            for (var n = (d - 1) * r + p, o = 0; o < s; o++)for (var m = l.insertRow(o), j = 0; j < v; j++) {
                var q = b(m.insertCell(j));
                q.addClass("ke-cell");
                h(q, j, n);
                var u = b('<span class="ke-img"></span>').css("background-position", "-" + 24 * n + "px 0px").css("background-image",
                    "url(" + f + "static.gif)");
                q.append(u);
                A.push(q);
                n++
            }
            return l
        }

        function n() {
            b.each(A, function () {
                this.unbind()
            })
        }

        function o(b, d) {
            b.click(function (b) {
                n();
                C.parentNode.removeChild(C);
                u.remove();
                C = m(d, q);
                l(d);
                e = d;
                b.stop()
            })
        }

        function l(d) {
            u = b('<div class="ke-page"></div>');
            q.append(u);
            for (var e = 1; e <= z; e++) {
                if (d !== e) {
                    var f = b('<a href="javascript:;">[' + e + "]</a>");
                    o(f, e);
                    u.append(f);
                    A.push(f)
                } else u.append(b("@[" + e + "]"));
                u.append(b("@&nbsp;"))
            }
        }

        var s = 5, v = 9, p = 0, r = s * v, z = Math.ceil(135 / r), D = Math.floor(v / 2), q = b('<div class="ke-plugin-emoticons"></div>'),
            A = [];
        d.createMenu({
            name: "emoticons", beforeRemove: function () {
                n()
            }
        }).div.append(q);
        var B, G;
        j && (B = b('<div class="ke-preview"></div>').css("right", 0), G = b('<img class="ke-preview-img" src="' + f + p + '.gif" />'), q.append(B), B.append(G));
        var C = m(e, q), u;
        l(e)
    })
});
KindEditor.plugin("filemanager", function (b) {
    function d(b, d) {
        d.is_dir ? b.attr("title", d.filename) : b.attr("title", d.filename + " (" + Math.ceil(d.filesize / 1024) + "KB, " + d.datetime + ")")
    }

    var f = this, j = b.undef(f.fileManagerJson, f.basePath + "php/file_manager_json.php"), e = f.pluginsPath + "filemanager/images/", h = f.lang("filemanager.");
    f.plugin.filemanagerDialog = function (m) {
        function n(d, e, h) {
            d = "path=" + d + "&order=" + e + "&dir=" + z;
            A.showLoading(f.lang("ajaxLoading"));
            b.ajax(b.addParam(j, d + "&" + (new Date).getTime()), function (b) {
                A.hideLoading();
                h(b)
            })
        }

        function o(d, e, f, h) {
            var l = b.formatUrl(e.current_url + f.filename, "absolute"), o = encodeURIComponent(e.current_dir_path + f.filename + "/");
            f.is_dir ? d.click(function () {
                n(o, u.val(), h)
            }) : f.is_photo ? d.click(function () {
                q.call(this, l, f.filename)
            }) : d.click(function () {
                q.call(this, l, f.filename)
            });
            I.push(d)
        }

        function l(d, e) {
            function f() {
                C.val() == "VIEW" ? n(d.current_dir_path, u.val(), v) : n(d.current_dir_path, u.val(), s)
            }

            b.each(I, function () {
                this.unbind()
            });
            G.unbind();
            C.unbind();
            u.unbind();
            d.current_dir_path && G.click(function () {
                n(d.moveup_dir_path,
                    u.val(), e)
            });
            C.change(f);
            u.change(f);
            B.html("")
        }

        function s(d) {
            l(d, s);
            var f = document.createElement("table");
            f.className = "ke-table";
            f.cellPadding = 0;
            f.cellSpacing = 0;
            f.border = 0;
            B.append(f);
            for (var n = d.file_list, m = 0, j = n.length; m < j; m++) {
                var r = n[m], p = b(f.insertRow(m));
                p.mouseover(function () {
                    b(this).addClass("ke-on")
                }).mouseout(function () {
                    b(this).removeClass("ke-on")
                });
                var q = b('<img src="' + (e + (r.is_dir ? "folder-16.gif" : "file-16.gif")) + '" width="16" height="16" alt="' + r.filename + '" align="absmiddle" />'), q =
                    b(p[0].insertCell(0)).addClass("ke-cell ke-name").append(q).append(document.createTextNode(" " + r.filename));
                !r.is_dir || r.has_file ? (p.css("cursor", "pointer"), q.attr("title", r.filename), o(q, d, r, s)) : q.attr("title", h.emptyFolder);
                b(p[0].insertCell(1)).addClass("ke-cell ke-size").html(r.is_dir ? "-" : Math.ceil(r.filesize / 1024) + "KB");
                b(p[0].insertCell(2)).addClass("ke-cell ke-datetime").html(r.datetime)
            }
        }

        function v(f) {
            l(f, v);
            for (var n = f.file_list, m = 0, s = n.length; m < s; m++) {
                var j = n[m], r = b('<div class="ke-inline-block ke-item"></div>');
                B.append(r);
                var p = b('<div class="ke-inline-block ke-photo"></div>').mouseover(function () {
                    b(this).addClass("ke-on")
                }).mouseout(function () {
                    b(this).removeClass("ke-on")
                });
                r.append(p);
                var q = f.current_url + j.filename, q = b('<img src="' + (j.is_dir ? e + "folder-64.gif" : j.is_photo ? q : e + "file-64.gif") + '" width="80" height="80" alt="' + j.filename + '" />');
                !j.is_dir || j.has_file ? (p.css("cursor", "pointer"), d(p, j), o(p, f, j, v)) : p.attr("title", h.emptyFolder);
                p.append(q);
                r.append('<div class="ke-name" title="' + j.filename + '">' +
                    j.filename + "</div>")
            }
        }

        var p = b.undef(m.width, 650), r = b.undef(m.height, 510), z = b.undef(m.dirName, ""), D = b.undef(m.viewType, "VIEW").toUpperCase(), q = m.clickFn, m = ['<div style="padding:10px 20px;"><div class="ke-plugin-filemanager-header"><div class="ke-left">', '<img class="ke-inline-block" name="moveupImg" src="' + e + 'go-up.gif" width="16" height="16" border="0" alt="" /> ', '<a class="ke-inline-block" name="moveupLink" href="javascript:;">' + h.moveup + "</a>", '</div><div class="ke-right">', h.viewType + ' <select class="ke-inline-block" name="viewType">',
            '<option value="VIEW">' + h.viewImage + "</option>", '<option value="LIST">' + h.listImage + "</option>", "</select> ", h.orderType + ' <select class="ke-inline-block" name="orderType">', '<option value="NAME">' + h.fileName + "</option>", '<option value="SIZE">' + h.fileSize + "</option>", '<option value="TYPE">' + h.fileType + "</option>", '</select></div><div class="ke-clearfix"></div></div><div class="ke-plugin-filemanager-body"></div></div>'].join(""), A = f.createDialog({
            name: "filemanager", width: p, height: r, title: f.lang("filemanager"),
            body: m
        }), p = A.div, B = b(".ke-plugin-filemanager-body", p);
        b('[name="moveupImg"]', p);
        var G = b('[name="moveupLink"]', p);
        b('[name="viewServer"]', p);
        var C = b('[name="viewType"]', p), u = b('[name="orderType"]', p), I = [];
        C.val(D);
        n("", u.val(), D == "VIEW" ? v : s);
        return A
    }
});
KindEditor.plugin("flash", function (b) {
    var d = this, f = d.lang("flash."), j = b.undef(d.allowFlashUpload, !0), e = b.undef(d.allowFileManager, !1), h = b.undef(d.formatUploadUrl, !0), m = b.undef(d.extraFileUploadParams, {}), n = b.undef(d.filePostName, "imgFile"), o = b.undef(d.uploadJson, d.basePath + "php/upload_json.php");
    d.plugin.flash = {
        edit: function () {
            var l = ['<div style="padding:20px;"><div class="ke-dialog-row">', '<label for="keUrl" style="width:60px;">' + f.url + "</label>", '<input class="ke-input-text" type="text" id="keUrl" name="url" value="" style="width:160px;" /> &nbsp;',
                '<input type="button" class="ke-upload-button" value="' + f.upload + '" /> &nbsp;', '<span class="ke-button-common ke-button-outer">', '<input type="button" class="ke-button-common ke-button" name="viewServer" value="' + f.viewServer + '" />', '</span></div><div class="ke-dialog-row">', '<label for="keWidth" style="width:60px;">' + f.width + "</label>", '<input type="text" id="keWidth" class="ke-input-text ke-input-number" name="width" value="550" maxlength="4" /> </div><div class="ke-dialog-row">', '<label for="keHeight" style="width:60px;">' +
                f.height + "</label>", '<input type="text" id="keHeight" class="ke-input-text ke-input-number" name="height" value="400" maxlength="4" /> </div></div>'].join(""), s = d.createDialog({
                name: "flash", width: 450, title: d.lang("flash"), body: l, yesBtn: {
                    name: d.lang("yes"), click: function () {
                        var e = b.trim(p.val()), f = r.val(), h = z.val();
                        e == "http://" || b.invalidUrl(e) ? (alert(d.lang("invalidUrl")), p[0].focus()) : /^\d*$/.test(f) ? /^\d*$/.test(h) ? (e = b.mediaImg(d.themesPath + "common/blank.gif", {
                            src: e, type: b.mediaType(".swf"), width: f,
                            height: h, quality: "high"
                        }), d.insertHtml(e).hideDialog().focus()) : (alert(d.lang("invalidHeight")), z[0].focus()) : (alert(d.lang("invalidWidth")), r[0].focus())
                    }
                }
            }), v = s.div, p = b('[name="url"]', v), l = b('[name="viewServer"]', v), r = b('[name="width"]', v), z = b('[name="height"]', v);
            p.val("http://");
            if (j) {
                var D = b.uploadbutton({
                    button: b(".ke-upload-button", v)[0], fieldName: n, extraParams: m, url: b.addParam(o, "dir=flash"), afterUpload: function (e) {
                        s.hideLoading();
                        if (e.error === 0) {
                            var f = e.url;
                            h && (f = b.formatUrl(f, "absolute"));
                            p.val(f);
                            d.afterUpload && d.afterUpload.call(d, f, e, "flash");
                            alert(d.lang("uploadSuccess"))
                        } else alert(e.message)
                    }, afterError: function (b) {
                        s.hideLoading();
                        d.errorDialog(b)
                    }
                });
                D.fileBox.change(function () {
                    s.showLoading(d.lang("uploadLoading"));
                    D.submit()
                })
            } else b(".ke-upload-button", v).hide();
            e ? l.click(function () {
                d.loadPlugin("filemanager", function () {
                    d.plugin.filemanagerDialog({
                        viewType: "LIST", dirName: "flash", clickFn: function (e) {
                            d.dialogs.length > 1 && (b('[name="url"]', v).val(e), d.afterSelectFile && d.afterSelectFile.call(d,
                                e), d.hideDialog())
                        }
                    })
                })
            }) : l.hide();
            if (l = d.plugin.getSelectedFlash()) {
                var q = b.mediaAttrs(l.attr("data-ke-tag"));
                p.val(q.src);
                r.val(b.removeUnit(l.css("width")) || q.width || 0);
                z.val(b.removeUnit(l.css("height")) || q.height || 0)
            }
            p[0].focus();
            p[0].select()
        }, "delete": function () {
            d.plugin.getSelectedFlash().remove();
            d.addBookmark()
        }
    };
    d.clickToolbar("flash", d.plugin.flash.edit)
});
KindEditor.plugin("image", function (b) {
    var d = this, f = b.undef(d.allowImageUpload, !0), j = b.undef(d.allowImageRemote, !0), e = b.undef(d.formatUploadUrl, !0), h = b.undef(d.allowFileManager, !1), m = b.undef(d.uploadJson, d.basePath + "php/upload_json.php"), n = b.undef(d.imageTabIndex, 0), o = d.pluginsPath + "image/images/", l = b.undef(d.extraFileUploadParams, {}), s = b.undef(d.filePostName, "imgFile"), v = b.undef(d.fillDescAfterUploadImage, !1), p = d.lang("image.");
    d.plugin.imageDialog = function (f) {
        function n(b, d) {
            M.val(b);
            K.val(d);
            W =
                b;
            S = d
        }

        b.undef(f.imageWidth, "");
        b.undef(f.imageHeight, "");
        b.undef(f.imageTitle, "");
        b.undef(f.imageAlign, "");
        var j = b.undef(f.showRemote, !0), q = b.undef(f.showLocal, !0), A = b.undef(f.tabIndex, 0), B = f.clickFn, G = "kindeditor_upload_iframe_" + (new Date).getTime(), C = [], u;
        for (u in l)C.push('<input type="hidden" name="' + u + '" value="' + l[u] + '" />');
        var C = ['<div style="padding:20px;"><div class="tabs"></div><div class="tab1" style="display:none;"><div class="ke-dialog-row">', '<label for="remoteUrl" style="width:60px;">' +
        p.remoteUrl + "</label>", '<input type="text" id="remoteUrl" class="ke-input-text" name="url" value="" style="width:200px;" /> &nbsp;<span class="ke-button-common ke-button-outer">', '<input type="button" class="ke-button-common ke-button" name="viewServer" value="' + p.viewServer + '" />', '</span></div><div class="ke-dialog-row">', '<label for="remoteWidth" style="width:60px;">' + p.size + "</label>", p.width + ' <input type="text" id="remoteWidth" class="ke-input-text ke-input-number" name="width" value="" maxlength="4" /> ',
            p.height + ' <input type="text" class="ke-input-text ke-input-number" name="height" value="" maxlength="4" /> ', '<img class="ke-refresh-btn" src="' + o + 'refresh.png" width="16" height="16" alt="" style="cursor:pointer;" title="' + p.resetSize + '" />', '</div><div class="ke-dialog-row">', '<label style="width:60px;">' + p.align + "</label>", '<input type="radio" name="align" class="ke-inline-block" value="" checked="checked" /> <img name="defaultImg" src="' + o + 'align_top.gif" width="23" height="25" alt="" />', ' <input type="radio" name="align" class="ke-inline-block" value="left" /> <img name="leftImg" src="' +
            o + 'align_left.gif" width="23" height="25" alt="" />', ' <input type="radio" name="align" class="ke-inline-block" value="right" /> <img name="rightImg" src="' + o + 'align_right.gif" width="23" height="25" alt="" />', '</div><div class="ke-dialog-row">', '<label for="remoteTitle" style="width:60px;">' + p.imgTitle + "</label>", '<input type="text" id="remoteTitle" class="ke-input-text" name="title" value="" style="width:200px;" /></div></div><div class="tab2" style="display:none;">', '<iframe name="' + G + '" style="display:none;"></iframe>',
            '<form class="ke-upload-area ke-form" method="post" enctype="multipart/form-data" target="' + G + '" action="' + b.addParam(m, "dir=image") + '">', '<div class="ke-dialog-row">', C.join(""), '<label style="width:60px;">' + p.localUrl + "</label>", '<input type="text" name="localUrl" class="ke-input-text" tabindex="-1" style="width:200px;" readonly="true" /> &nbsp;', '<input type="button" class="ke-upload-button" value="' + p.upload + '" />', "</div></form></div></div>"].join(""), I = d.createDialog({
            name: "image", width: q ||
            h ? 450 : 400, height: q && j ? 300 : 250, title: d.lang("image"), body: C, yesBtn: {
                name: d.lang("yes"), click: function () {
                    if (!I.isLoading)if (q && j && H && H.selectedIndex === 1 || !j)U.fileBox.val() == "" ? alert(d.lang("pleaseSelectFile")) : (I.showLoading(d.lang("uploadLoading")), U.submit(), qa.val("")); else {
                        var e = b.trim(T.val()), f = M.val(), h = K.val(), l = Q.val(), n = "";
                        R.each(function () {
                            if (this.checked)return n = this.value, !1
                        });
                        e == "http://" || b.invalidUrl(e) ? (alert(d.lang("invalidUrl")), T[0].focus()) : /^\d*$/.test(f) ? /^\d*$/.test(h) ? B.call(d,
                            e, l, f, h, 0, n) : (alert(d.lang("invalidHeight")), K[0].focus()) : (alert(d.lang("invalidWidth")), M[0].focus())
                    }
                }
            }, beforeRemove: function () {
                $.unbind();
                M.unbind();
                K.unbind();
                O.unbind()
            }
        }), E = I.div, T = b('[name="url"]', E), qa = b('[name="localUrl"]', E), $ = b('[name="viewServer"]', E), M = b('.tab1 [name="width"]', E), K = b('.tab1 [name="height"]', E), O = b(".ke-refresh-btn", E), Q = b('.tab1 [name="title"]', E), R = b('.tab1 [name="align"]', E), H;
        j && q ? (H = b.tabs({
            src: b(".tabs", E), afterSelect: function () {
            }
        }), H.add({
            title: p.remoteImage, panel: b(".tab1",
                E)
        }), H.add({title: p.localImage, panel: b(".tab2", E)}), H.select(A)) : j ? b(".tab1", E).show() : q && b(".tab2", E).show();
        var U = b.uploadbutton({
            button: b(".ke-upload-button", E)[0], fieldName: s, form: b(".ke-form", E), target: G, width: 60, afterUpload: function (f) {
                I.hideLoading();
                if (f.error === 0) {
                    var h = f.url;
                    e && (h = b.formatUrl(h, "absolute"));
                    d.afterUpload && d.afterUpload.call(d, h, f, "image");
                    v ? (b(".ke-dialog-row #remoteUrl", E).val(h), b(".ke-tabs-li", E)[0].click(), b(".ke-refresh-btn", E).click()) : B.call(d, h, f.title, f.width, f.height,
                        f.border, f.align)
                } else alert(f.message)
            }, afterError: function (b) {
                I.hideLoading();
                d.errorDialog(b)
            }
        });
        U.fileBox.change(function () {
            qa.val(U.fileBox.val())
        });
        h ? $.click(function () {
            d.loadPlugin("filemanager", function () {
                d.plugin.filemanagerDialog({
                    viewType: "VIEW", dirName: "image", clickFn: function (e) {
                        d.dialogs.length > 1 && (b('[name="url"]', E).val(e), d.afterSelectFile && d.afterSelectFile.call(d, e), d.hideDialog())
                    }
                })
            })
        }) : $.hide();
        var W = 0, S = 0;
        O.click(function () {
            var d = b('<img src="' + T.val() + '" />', document).css({
                position: "absolute",
                visibility: "hidden", top: 0, left: "-1000px"
            });
            d.bind("load", function () {
                n(d.width(), d.height());
                d.remove()
            });
            b(document.body).append(d)
        });
        M.change(function () {
            W > 0 && K.val(Math.round(S / W * parseInt(this.value, 10)))
        });
        K.change(function () {
            S > 0 && M.val(Math.round(W / S * parseInt(this.value, 10)))
        });
        T.val(f.imageUrl);
        n(f.imageWidth, f.imageHeight);
        Q.val(f.imageTitle);
        R.each(function () {
            if (this.value === f.imageAlign)return this.checked = !0, !1
        });
        j && A === 0 && (T[0].focus(), T[0].select());
        return I
    };
    d.plugin.image = {
        edit: function () {
            var b =
                d.plugin.getSelectedImage();
            d.plugin.imageDialog({
                imageUrl: b ? b.attr("data-ke-src") : "http://",
                imageWidth: b ? b.width() : "",
                imageHeight: b ? b.height() : "",
                imageTitle: b ? b.attr("title") : "",
                imageAlign: b ? b.attr("align") : "",
                showRemote: j,
                showLocal: f,
                tabIndex: b ? 0 : n,
                clickFn: function (e, f, h, l, n, o) {
                    b ? (b.attr("src", e), b.attr("data-ke-src", e), b.attr("width", h), b.attr("height", l), b.attr("title", f), b.attr("align", o), b.attr("alt", f)) : d.exec("insertimage", e, f, h, l, n, o);
                    setTimeout(function () {
                        d.hideDialog().focus()
                    }, 0)
                }
            })
        },
        "delete": function () {
            var b = d.plugin.getSelectedImage();
            b.parent().name == "a" && (b = b.parent());
            b.remove();
            d.addBookmark()
        }
    };
    d.clickToolbar("image", d.plugin.image.edit)
});
KindEditor.plugin("insertfile", function (b) {
    var d = this, f = b.undef(d.allowFileUpload, !0), j = b.undef(d.allowFileManager, !1), e = b.undef(d.formatUploadUrl, !0), h = b.undef(d.uploadJson, d.basePath + "php/upload_json.php"), m = b.undef(d.extraFileUploadParams, {}), n = b.undef(d.filePostName, "imgFile"), o = d.lang("insertfile.");
    d.plugin.fileDialog = function (l) {
        var s = b.undef(l.fileUrl, "http://"), v = b.undef(l.fileTitle, ""), p = l.clickFn, l = ['<div style="padding:20px;"><div class="ke-dialog-row">', '<label for="keUrl" style="width:60px;">' +
            o.url + "</label>", '<input type="text" id="keUrl" name="url" class="ke-input-text" style="width:160px;" /> &nbsp;', '<input type="button" class="ke-upload-button" value="' + o.upload + '" /> &nbsp;', '<span class="ke-button-common ke-button-outer">', '<input type="button" class="ke-button-common ke-button" name="viewServer" value="' + o.viewServer + '" />', '</span></div><div class="ke-dialog-row">', '<label for="keTitle" style="width:60px;">' + o.title + "</label>", '<input type="text" id="keTitle" class="ke-input-text" name="title" value="" style="width:160px;" /></div></div></form></div>'].join(""),
            r = d.createDialog({
                name: "insertfile", width: 450, title: d.lang("insertfile"), body: l, yesBtn: {
                    name: d.lang("yes"), click: function () {
                        var e = b.trim(D.val()), f = q.val();
                        e == "http://" || b.invalidUrl(e) ? (alert(d.lang("invalidUrl")), D[0].focus()) : (b.trim(f) === "" && (f = e), p.call(d, e, f))
                    }
                }
            }), z = r.div, D = b('[name="url"]', z), l = b('[name="viewServer"]', z), q = b('[name="title"]', z);
        if (f) {
            var A = b.uploadbutton({
                button: b(".ke-upload-button", z)[0], fieldName: n, url: b.addParam(h, "dir=file"), extraParams: m, afterUpload: function (f) {
                    r.hideLoading();
                    if (f.error === 0) {
                        var h = f.url;
                        e && (h = b.formatUrl(h, "absolute"));
                        D.val(h);
                        d.afterUpload && d.afterUpload.call(d, h, f, "insertfile");
                        alert(d.lang("uploadSuccess"))
                    } else alert(f.message)
                }, afterError: function (b) {
                    r.hideLoading();
                    d.errorDialog(b)
                }
            });
            A.fileBox.change(function () {
                r.showLoading(d.lang("uploadLoading"));
                A.submit()
            })
        } else b(".ke-upload-button", z).hide();
        j ? l.click(function () {
            d.loadPlugin("filemanager", function () {
                d.plugin.filemanagerDialog({
                    viewType: "LIST", dirName: "file", clickFn: function (e) {
                        d.dialogs.length >
                        1 && (b('[name="url"]', z).val(e), d.afterSelectFile && d.afterSelectFile.call(d, e), d.hideDialog())
                    }
                })
            })
        }) : l.hide();
        D.val(s);
        q.val(v);
        D[0].focus();
        D[0].select()
    };
    d.clickToolbar("insertfile", function () {
        d.plugin.fileDialog({
            clickFn: function (b, e) {
                d.insertHtml('<a class="ke-insertfile" href="' + b + '" data-ke-src="' + b + '" target="_blank">' + e + "</a>").hideDialog().focus()
            }
        })
    })
});
KindEditor.plugin("lineheight", function (b) {
    var d = this, f = d.lang("lineheight.");
    d.clickToolbar("lineheight", function () {
        var j = "", e = d.cmd.commonNode({"*": ".line-height"});
        e && (j = e.css("line-height"));
        var h = d.createMenu({name: "lineheight", width: 150});
        b.each(f.lineHeight, function (e, f) {
            b.each(f, function (b, e) {
                h.addItem({
                    title: e, checked: j === b, click: function () {
                        d.cmd.toggle('<span style="line-height:' + b + ';"></span>', {span: ".line-height=" + b});
                        d.updateState();
                        d.addBookmark();
                        d.hideMenu()
                    }
                })
            })
        })
    })
});
KindEditor.plugin("link", function (b) {
    var d = this;
    d.plugin.link = {
        edit: function () {
            var f = d.lang("link."), j = '<div style="padding:20px;"><div class="ke-dialog-row"><label for="keUrl" style="width:60px;">' + f.url + '</label><input class="ke-input-text" type="text" id="keUrl" name="url" value="" style="width:260px;" /></div><div class="ke-dialog-row""><label for="keType" style="width:60px;">' + f.linkType + '</label><select id="keType" name="type"></select></div></div>', j = d.createDialog({
                name: "link", width: 450, title: d.lang("link"),
                body: j, yesBtn: {
                    name: d.lang("yes"), click: function () {
                        var f = b.trim(e.val());
                        f == "http://" || b.invalidUrl(f) ? (alert(d.lang("invalidUrl")), e[0].focus()) : d.exec("createlink", f, h.val()).hideDialog().focus()
                    }
                }
            }).div, e = b('input[name="url"]', j), h = b('select[name="type"]', j);
            e.val("http://");
            h[0].options[0] = new Option(f.newWindow, "_blank");
            h[0].options[1] = new Option(f.selfWindow, "");
            d.cmd.selection();
            if (f = d.plugin.getSelectedLink())d.cmd.range.selectNode(f[0]), d.cmd.select(), e.val(f.attr("data-ke-src")), h.val(f.attr("target"));
            e[0].focus();
            e[0].select()
        }, "delete": function () {
            d.exec("unlink", null)
        }
    };
    d.clickToolbar("link", d.plugin.link.edit)
});
KindEditor.plugin("map", function (b) {
    var d = this, f = d.lang("map.");
    d.clickToolbar("map", function () {
        function j() {
            n = l[0].contentWindow;
            o = b.iframeDoc(l)
        }

        var e = ['<div style="padding:10px 20px;"><div class="ke-dialog-row">', f.address + ' <input id="kindeditor_plugin_map_address" name="address" class="ke-input-text" value="" style="width:200px;" /> ', '<span class="ke-button-common ke-button-outer">', '<input type="button" name="searchBtn" class="ke-button-common ke-button" value="' + f.search + '" />', '</span></div><div class="ke-map" style="width:558px;height:360px;"></div></div>'].join(""),
            e = d.createDialog({
                name: "map", width: 600, title: d.lang("map"), body: e, yesBtn: {
                    name: d.lang("yes"), click: function () {
                        var b = n.map, e = b.getCenter().lat() + "," + b.getCenter().lng(), f = b.getZoom(), b = b.getMapTypeId(), h = "http://maps.googleapis.com/maps/api/staticmap";
                        h += "?center=" + encodeURIComponent(e);
                        h += "&zoom=" + encodeURIComponent(f);
                        h += "&size=558x360";
                        h += "&maptype=" + encodeURIComponent(b);
                        h += "&markers=" + encodeURIComponent(e);
                        h += "&language=" + d.langType;
                        h += "&sensor=false";
                        d.exec("insertimage", h).hideDialog().focus()
                    }
                },
                beforeRemove: function () {
                    m.remove();
                    o && o.write("");
                    l.remove()
                }
            }).div, h = b('[name="address"]', e), m = b('[name="searchBtn"]', e), n, o;
        ['<!doctype html><html><head>\n<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />\n<style>\n\thtml { height: 100% }\n\tbody { height: 100%; margin: 0; padding: 0; background-color: #FFF }\n\t#map_canvas { height: 100% }\n</style>', '<script src="http://maps.googleapis.com/maps/api/js?sensor=false&language=' + d.langType + '"><\/script>', '<script>\nvar map, geocoder;\nfunction initialize() {\n\tvar latlng = new google.maps.LatLng(31.230393, 121.473704);\n\tvar options = {\n\t\tzoom: 11,\n\t\tcenter: latlng,\n\t\tdisableDefaultUI: true,\n\t\tpanControl: true,\n\t\tzoomControl: true,\n\t\tmapTypeControl: true,\n\t\tscaleControl: true,\n\t\tstreetViewControl: false,\n\t\toverviewMapControl: true,\n\t\tmapTypeId: google.maps.MapTypeId.ROADMAP\n\t};\n\tmap = new google.maps.Map(document.getElementById("map_canvas"), options);\n\tgeocoder = new google.maps.Geocoder();\n\tgeocoder.geocode({latLng: latlng}, function(results, status) {\n\t\tif (status == google.maps.GeocoderStatus.OK) {\n\t\t\tif (results[3]) {\n\t\t\t\tparent.document.getElementById("kindeditor_plugin_map_address").value = results[3].formatted_address;\n\t\t\t}\n\t\t}\n\t});\n}\nfunction search(address) {\n\tif (!map) return;\n\tgeocoder.geocode({address : address}, function(results, status) {\n\t\tif (status == google.maps.GeocoderStatus.OK) {\n\t\t\tmap.setZoom(11);\n\t\t\tmap.setCenter(results[0].geometry.location);\n\t\t\tvar marker = new google.maps.Marker({\n\t\t\t\tmap: map,\n\t\t\t\tposition: results[0].geometry.location\n\t\t\t});\n\t\t} else {\n\t\t\talert("Invalid address: " + address);\n\t\t}\n\t});\n}\n<\/script>\n</head>\n<body onload="initialize();">\n<div id="map_canvas" style="width:100%; height:100%"></div>\n</body></html>'].join("\n");
        var l = b('<iframe class="ke-textarea" frameborder="0" src="' + d.pluginsPath + 'map/map.html" style="width:558px;height:360px;"></iframe>');
        l.bind("load", function () {
            l.unbind("load");
            b.IE ? j() : setTimeout(j, 0)
        });
        b(".ke-map", e).replaceWith(l);
        m.click(function () {
            n.search(h.val())
        })
    })
});
KindEditor.plugin("media", function (b) {
    var d = this, f = d.lang("media."), j = b.undef(d.allowMediaUpload, !0), e = b.undef(d.allowFileManager, !1), h = b.undef(d.formatUploadUrl, !0), m = b.undef(d.extraFileUploadParams, {}), n = b.undef(d.filePostName, "imgFile"), o = b.undef(d.uploadJson, d.basePath + "php/upload_json.php");
    d.plugin.media = {
        edit: function () {
            var l = ['<div style="padding:20px;"><div class="ke-dialog-row">', '<label for="keUrl" style="width:60px;">' + f.url + "</label>", '<input class="ke-input-text" type="text" id="keUrl" name="url" value="" style="width:160px;" /> &nbsp;',
                '<input type="button" class="ke-upload-button" value="' + f.upload + '" /> &nbsp;', '<span class="ke-button-common ke-button-outer">', '<input type="button" class="ke-button-common ke-button" name="viewServer" value="' + f.viewServer + '" />', '</span></div><div class="ke-dialog-row">', '<label for="keWidth" style="width:60px;">' + f.width + "</label>", '<input type="text" id="keWidth" class="ke-input-text ke-input-number" name="width" value="550" maxlength="4" /></div><div class="ke-dialog-row">', '<label for="keHeight" style="width:60px;">' +
                f.height + "</label>", '<input type="text" id="keHeight" class="ke-input-text ke-input-number" name="height" value="400" maxlength="4" /></div><div class="ke-dialog-row">', '<label for="keAutostart">' + f.autostart + "</label>", '<input type="checkbox" id="keAutostart" name="autostart" value="" /> </div></div>'].join(""), s = d.createDialog({
                name: "media", width: 450, height: 230, title: d.lang("media"), body: l, yesBtn: {
                    name: d.lang("yes"), click: function () {
                        var e = b.trim(p.val()), f = r.val(), h = z.val();
                        e == "http://" || b.invalidUrl(e) ?
                            (alert(d.lang("invalidUrl")), p[0].focus()) : /^\d*$/.test(f) ? /^\d*$/.test(h) ? (e = b.mediaImg(d.themesPath + "common/blank.gif", {
                            src: e,
                            type: b.mediaType(e),
                            width: f,
                            height: h,
                            autostart: D[0].checked ? "true" : "false",
                            loop: "true"
                        }), d.insertHtml(e).hideDialog().focus()) : (alert(d.lang("invalidHeight")), z[0].focus()) : (alert(d.lang("invalidWidth")), r[0].focus())
                    }
                }
            }), v = s.div, p = b('[name="url"]', v), l = b('[name="viewServer"]', v), r = b('[name="width"]', v), z = b('[name="height"]', v), D = b('[name="autostart"]', v);
            p.val("http://");
            if (j) {
                var q = b.uploadbutton({
                    button: b(".ke-upload-button", v)[0], fieldName: n, extraParams: m, url: b.addParam(o, "dir=media"), afterUpload: function (e) {
                        s.hideLoading();
                        if (e.error === 0) {
                            var f = e.url;
                            h && (f = b.formatUrl(f, "absolute"));
                            p.val(f);
                            d.afterUpload && d.afterUpload.call(d, f, e, "media");
                            alert(d.lang("uploadSuccess"))
                        } else alert(e.message)
                    }, afterError: function (b) {
                        s.hideLoading();
                        d.errorDialog(b)
                    }
                });
                q.fileBox.change(function () {
                    s.showLoading(d.lang("uploadLoading"));
                    q.submit()
                })
            } else b(".ke-upload-button", v).hide();
            e ? l.click(function () {
                d.loadPlugin("filemanager", function () {
                    d.plugin.filemanagerDialog({
                        viewType: "LIST", dirName: "media", clickFn: function (e) {
                            d.dialogs.length > 1 && (b('[name="url"]', v).val(e), d.afterSelectFile && d.afterSelectFile.call(d, e), d.hideDialog())
                        }
                    })
                })
            }) : l.hide();
            if (l = d.plugin.getSelectedMedia()) {
                var A = b.mediaAttrs(l.attr("data-ke-tag"));
                p.val(A.src);
                r.val(b.removeUnit(l.css("width")) || A.width || 0);
                z.val(b.removeUnit(l.css("height")) || A.height || 0);
                D[0].checked = A.autostart === "true"
            }
            p[0].focus();
            p[0].select()
        },
        "delete": function () {
            d.plugin.getSelectedMedia().remove();
            d.addBookmark()
        }
    };
    d.clickToolbar("media", d.plugin.media.edit)
});
(function (b) {
    function d(b) {
        this.init(b)
    }

    b.extend(d, {
        init: function (d) {
            function j(d, e) {
                b(".ke-status > div", d).hide();
                b(".ke-message", d).addClass("ke-error").show().html(b.escape(e))
            }

            var e = this;
            d.afterError = d.afterError || function (b) {
                    alert(b)
                };
            e.options = d;
            e.progressbars = {};
            e.div = b(d.container).html(['<div class="ke-swfupload"><div class="ke-swfupload-top"><div class="ke-inline-block ke-swfupload-button"><input type="button" value="Browse" /></div>', '<div class="ke-inline-block ke-swfupload-desc">' + d.uploadDesc +
            "</div>", '<span class="ke-button-common ke-button-outer ke-swfupload-startupload">', '<input type="button" class="ke-button-common ke-button" value="' + d.startButtonValue + '" />', '</span></div><div class="ke-swfupload-body"></div></div>'].join(""));
            e.bodyDiv = b(".ke-swfupload-body", e.div);
            var h = {
                debug: !1,
                upload_url: d.uploadUrl,
                flash_url: d.flashUrl,
                file_post_name: d.filePostName,
                button_placeholder: b(".ke-swfupload-button > input", e.div)[0],
                button_image_url: d.buttonImageUrl,
                button_width: d.buttonWidth,
                button_height: d.buttonHeight,
                button_cursor: SWFUpload.CURSOR.HAND,
                file_types: d.fileTypes,
                file_types_description: d.fileTypesDesc,
                file_upload_limit: d.fileUploadLimit,
                file_size_limit: d.fileSizeLimit,
                post_params: d.postParams,
                file_queued_handler: function (b) {
                    b.url = e.options.fileIconUrl;
                    e.appendFile(b)
                },
                file_queue_error_handler: function (e, h) {
                    var o = "";
                    switch (h) {
                        case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                            o = d.queueLimitExceeded;
                            break;
                        case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                            o = d.fileExceedsSizeLimit;
                            break;
                        case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                            o =
                                d.zeroByteFile;
                            break;
                        case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                            o = d.invalidFiletype;
                            break;
                        default:
                            o = d.unknownError
                    }
                    b.DEBUG && alert(o)
                },
                upload_start_handler: function (d) {
                    d = b('div[data-id="' + d.id + '"]', this.bodyDiv);
                    b(".ke-status > div", d).hide();
                    b(".ke-progressbar", d).show()
                },
                upload_progress_handler: function (b, d, f) {
                    d = Math.round(d * 100 / f);
                    b = e.progressbars[b.id];
                    b.bar.css("width", Math.round(d * 80 / 100) + "px");
                    b.percent.html(d + "%")
                },
                upload_error_handler: function (d) {
                    d && d.filestatus == SWFUpload.FILE_STATUS.ERROR &&
                    (d = b('div[data-id="' + d.id + '"]', e.bodyDiv).eq(0), j(d, e.options.errorMessage))
                },
                upload_success_handler: function (d, f) {
                    var h = b('div[data-id="' + d.id + '"]', e.bodyDiv).eq(0), l = {};
                    try {
                        l = b.json(f)
                    } catch (s) {
                        e.options.afterError.call(this, "<!doctype html><html>" + f + "</html>")
                    }
                    l.error !== 0 ? j(h, b.DEBUG ? l.message : e.options.errorMessage) : (d.url = l.url, b(".ke-img", h).attr("src", d.url).attr("data-status", d.filestatus).data("data", l), b(".ke-status > div", h).hide())
                }
            };
            e.swfu = new SWFUpload(h);
            b(".ke-swfupload-startupload input",
                e.div).click(function () {
                    e.swfu.startUpload()
                })
        }, getUrlList: function () {
            var d = [];
            b(".ke-img", self.bodyDiv).each(function () {
                var j = b(this);
                j.attr("data-status") == SWFUpload.FILE_STATUS.COMPLETE && d.push(j.data("data"))
            });
            return d
        }, removeFile: function (d) {
            this.swfu.cancelUpload(d);
            d = b('div[data-id="' + d + '"]', this.bodyDiv);
            b(".ke-photo", d).unbind();
            b(".ke-delete", d).unbind();
            d.remove()
        }, removeFiles: function () {
            var d = this;
            b(".ke-item", d.bodyDiv).each(function () {
                d.removeFile(b(this).attr("data-id"))
            })
        }, appendFile: function (d) {
            var j =
                this, e = b('<div class="ke-inline-block ke-item" data-id="' + d.id + '"></div>');
            j.bodyDiv.append(e);
            var h = b('<div class="ke-inline-block ke-photo"></div>').mouseover(function () {
                b(this).addClass("ke-on")
            }).mouseout(function () {
                b(this).removeClass("ke-on")
            });
            e.append(h);
            var m = b('<img src="' + d.url + '" class="ke-img" data-status="' + d.filestatus + '" width="80" height="80" alt="' + d.name + '" />');
            h.append(m);
            b('<span class="ke-delete"></span>').appendTo(h).click(function () {
                j.removeFile(d.id)
            });
            m = b('<div class="ke-status"></div>').appendTo(h);
            b('<div class="ke-progressbar"><div class="ke-progressbar-bar"><div class="ke-progressbar-bar-inner"></div></div><div class="ke-progressbar-percent">0%</div></div>').hide().appendTo(m);
            b('<div class="ke-message">' + j.options.pendingMessage + "</div>").appendTo(m);
            e.append('<div class="ke-name">' + d.name + "</div>");
            j.progressbars[d.id] = {bar: b(".ke-progressbar-bar-inner", h), percent: b(".ke-progressbar-percent", h)}
        }, remove: function () {
            this.removeFiles();
            this.swfu.destroy();
            this.div.html("")
        }
    });
    b.swfupload =
        function (b, j) {
            return new d(b, j)
        }
})(KindEditor);
KindEditor.plugin("multiimage", function (b) {
    var d = this;
    b.undef(d.formatUploadUrl, !0);
    var f = b.undef(d.uploadJson, d.basePath + "php/upload_json.php"), j = d.pluginsPath + "multiimage/images/", e = b.undef(d.imageSizeLimit, "1MB");
    b.undef(d.imageFileTypes, "*.jpg;*.gif;*.png");
    var h = b.undef(d.imageUploadLimit, 20), m = b.undef(d.filePostName, "imgFile"), n = d.lang("multiimage.");
    d.plugin.multiImageDialog = function (o) {
        var l = o.clickFn, o = b.tmpl(n.uploadDesc, {uploadLimit: h, sizeLimit: e}), s = d.createDialog({
            name: "multiimage", width: 650,
            height: 510, title: d.lang("multiimage"), body: '<div style="padding:20px;"><div class="swfupload"></div></div>', previewBtn: {
                name: n.insertAll, click: function () {
                    l.call(d, v.getUrlList())
                }
            }, yesBtn: {
                name: n.clearAll, click: function () {
                    v.removeFiles()
                }
            }, beforeRemove: function () {
                (!b.IE || b.V <= 8) && v.remove()
            }
        }), v = b.swfupload({
            container: b(".swfupload", s.div),
            buttonImageUrl: j + (d.langType == "zh_CN" ? "select-files-zh_CN.png" : "select-files-en.png"),
            buttonWidth: d.langType == "zh_CN" ? 72 : 88,
            buttonHeight: 23,
            fileIconUrl: j + "image.png",
            uploadDesc: o,
            startButtonValue: n.startUpload,
            uploadUrl: b.addParam(f, "dir=image"),
            flashUrl: j + "swfupload.swf",
            filePostName: m,
            fileTypes: "*.jpg;*.jpeg;*.gif;*.png;*.bmp",
            fileTypesDesc: "Image Files",
            fileUploadLimit: h,
            fileSizeLimit: e,
            postParams: b.undef(d.extraFileUploadParams, {}),
            queueLimitExceeded: n.queueLimitExceeded,
            fileExceedsSizeLimit: n.fileExceedsSizeLimit,
            zeroByteFile: n.zeroByteFile,
            invalidFiletype: n.invalidFiletype,
            unknownError: n.unknownError,
            pendingMessage: n.pending,
            errorMessage: n.uploadError,
            afterError: function (b) {
                d.errorDialog(b)
            }
        });
        return s
    };
    d.clickToolbar("multiimage", function () {
        d.plugin.multiImageDialog({
            clickFn: function (e) {
                e.length !== 0 && (b.each(e, function (b, e) {
                    d.afterUpload && d.afterUpload.call(d, e.url, e, "multiimage");
                    d.exec("insertimage", e.url, e.title, e.width, e.height, e.border, e.align)
                }), setTimeout(function () {
                    d.hideDialog().focus()
                }, 0))
            }
        })
    })
});
(function () {
    window.SWFUpload = function (b) {
        this.initSWFUpload(b)
    };
    SWFUpload.prototype.initSWFUpload = function (b) {
        try {
            this.customSettings = {}, this.settings = b, this.eventQueue = [], this.movieName = "KindEditor_SWFUpload_" + SWFUpload.movieCount++, this.movieElement = null, SWFUpload.instances[this.movieName] = this, this.initSettings(), this.loadFlash(), this.displayDebugInfo()
        } catch (d) {
            throw delete SWFUpload.instances[this.movieName], d;
        }
    };
    SWFUpload.instances = {};
    SWFUpload.movieCount = 0;
    SWFUpload.version = "2.2.0 2009-03-25";
    SWFUpload.QUEUE_ERROR = {QUEUE_LIMIT_EXCEEDED: -100, FILE_EXCEEDS_SIZE_LIMIT: -110, ZERO_BYTE_FILE: -120, INVALID_FILETYPE: -130};
    SWFUpload.UPLOAD_ERROR = {
        HTTP_ERROR: -200,
        MISSING_UPLOAD_URL: -210,
        IO_ERROR: -220,
        SECURITY_ERROR: -230,
        UPLOAD_LIMIT_EXCEEDED: -240,
        UPLOAD_FAILED: -250,
        SPECIFIED_FILE_ID_NOT_FOUND: -260,
        FILE_VALIDATION_FAILED: -270,
        FILE_CANCELLED: -280,
        UPLOAD_STOPPED: -290
    };
    SWFUpload.FILE_STATUS = {QUEUED: -1, IN_PROGRESS: -2, ERROR: -3, COMPLETE: -4, CANCELLED: -5};
    SWFUpload.BUTTON_ACTION = {
        SELECT_FILE: -100, SELECT_FILES: -110,
        START_UPLOAD: -120
    };
    SWFUpload.CURSOR = {ARROW: -1, HAND: -2};
    SWFUpload.WINDOW_MODE = {WINDOW: "window", TRANSPARENT: "transparent", OPAQUE: "opaque"};
    SWFUpload.completeURL = function (b) {
        if (typeof b !== "string" || b.match(/^https?:\/\//i) || b.match(/^\//))return b;
        var d = window.location.pathname.lastIndexOf("/");
        path = d <= 0 ? "/" : window.location.pathname.substr(0, d) + "/";
        return path + b
    };
    SWFUpload.prototype.initSettings = function () {
        this.ensureDefault = function (b, d) {
            this.settings[b] = this.settings[b] == void 0 ? d : this.settings[b]
        };
        this.ensureDefault("upload_url", "");
        this.ensureDefault("preserve_relative_urls", !1);
        this.ensureDefault("file_post_name", "Filedata");
        this.ensureDefault("post_params", {});
        this.ensureDefault("use_query_string", !1);
        this.ensureDefault("requeue_on_error", !1);
        this.ensureDefault("http_success", []);
        this.ensureDefault("assume_success_timeout", 0);
        this.ensureDefault("file_types", "*.*");
        this.ensureDefault("file_types_description", "All Files");
        this.ensureDefault("file_size_limit", 0);
        this.ensureDefault("file_upload_limit",
            0);
        this.ensureDefault("file_queue_limit", 0);
        this.ensureDefault("flash_url", "swfupload.swf");
        this.ensureDefault("prevent_swf_caching", !0);
        this.ensureDefault("button_image_url", "");
        this.ensureDefault("button_width", 1);
        this.ensureDefault("button_height", 1);
        this.ensureDefault("button_text", "");
        this.ensureDefault("button_text_style", "color: #000000; font-size: 16pt;");
        this.ensureDefault("button_text_top_padding", 0);
        this.ensureDefault("button_text_left_padding", 0);
        this.ensureDefault("button_action", SWFUpload.BUTTON_ACTION.SELECT_FILES);
        this.ensureDefault("button_disabled", !1);
        this.ensureDefault("button_placeholder_id", "");
        this.ensureDefault("button_placeholder", null);
        this.ensureDefault("button_cursor", SWFUpload.CURSOR.ARROW);
        this.ensureDefault("button_window_mode", SWFUpload.WINDOW_MODE.WINDOW);
        this.ensureDefault("debug", !1);
        this.settings.debug_enabled = this.settings.debug;
        this.settings.return_upload_start_handler = this.returnUploadStart;
        this.ensureDefault("swfupload_loaded_handler", null);
        this.ensureDefault("file_dialog_start_handler",
            null);
        this.ensureDefault("file_queued_handler", null);
        this.ensureDefault("file_queue_error_handler", null);
        this.ensureDefault("file_dialog_complete_handler", null);
        this.ensureDefault("upload_start_handler", null);
        this.ensureDefault("upload_progress_handler", null);
        this.ensureDefault("upload_error_handler", null);
        this.ensureDefault("upload_success_handler", null);
        this.ensureDefault("upload_complete_handler", null);
        this.ensureDefault("debug_handler", this.debugMessage);
        this.ensureDefault("custom_settings", {});
        this.customSettings = this.settings.custom_settings;
        if (this.settings.prevent_swf_caching)this.settings.flash_url = this.settings.flash_url + (this.settings.flash_url.indexOf("?") < 0 ? "?" : "&") + "preventswfcaching=" + (new Date).getTime();
        if (!this.settings.preserve_relative_urls)this.settings.upload_url = SWFUpload.completeURL(this.settings.upload_url), this.settings.button_image_url = SWFUpload.completeURL(this.settings.button_image_url);
        delete this.ensureDefault
    };
    SWFUpload.prototype.loadFlash = function () {
        var b, d;
        if (document.getElementById(this.movieName) !== null)throw"ID " + this.movieName + " is already in use. The Flash Object could not be added";
        b = document.getElementById(this.settings.button_placeholder_id) || this.settings.button_placeholder;
        if (b == void 0)throw"Could not find the placeholder element: " + this.settings.button_placeholder_id;
        d = document.createElement("div");
        d.innerHTML = this.getFlashHTML();
        b.parentNode.replaceChild(d.firstChild, b);
        window[this.movieName] == void 0 && (window[this.movieName] = this.getMovieElement())
    };
    SWFUpload.prototype.getFlashHTML = function () {
        var b = "";
        KindEditor.IE && KindEditor.V > 8 && (b = ' classid = "clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"');
        return ['<object id="', this.movieName, '"' + b + ' type="application/x-shockwave-flash" data="', this.settings.flash_url, '" width="', this.settings.button_width, '" height="', this.settings.button_height, '" class="swfupload"><param name="wmode" value="', this.settings.button_window_mode, '" /><param name="movie" value="', this.settings.flash_url, '" /><param name="quality" value="high" /><param name="menu" value="false" /><param name="allowScriptAccess" value="always" />',
            '<param name="flashvars" value="' + this.getFlashVars() + '" />', "</object>"].join("")
    };
    SWFUpload.prototype.getFlashVars = function () {
        var b = this.buildParamString(), d = this.settings.http_success.join(",");
        return ["movieName=", encodeURIComponent(this.movieName), "&amp;uploadURL=", encodeURIComponent(this.settings.upload_url), "&amp;useQueryString=", encodeURIComponent(this.settings.use_query_string), "&amp;requeueOnError=", encodeURIComponent(this.settings.requeue_on_error), "&amp;httpSuccess=", encodeURIComponent(d),
            "&amp;assumeSuccessTimeout=", encodeURIComponent(this.settings.assume_success_timeout), "&amp;params=", encodeURIComponent(b), "&amp;filePostName=", encodeURIComponent(this.settings.file_post_name), "&amp;fileTypes=", encodeURIComponent(this.settings.file_types), "&amp;fileTypesDescription=", encodeURIComponent(this.settings.file_types_description), "&amp;fileSizeLimit=", encodeURIComponent(this.settings.file_size_limit), "&amp;fileUploadLimit=", encodeURIComponent(this.settings.file_upload_limit), "&amp;fileQueueLimit=",
            encodeURIComponent(this.settings.file_queue_limit), "&amp;debugEnabled=", encodeURIComponent(this.settings.debug_enabled), "&amp;buttonImageURL=", encodeURIComponent(this.settings.button_image_url), "&amp;buttonWidth=", encodeURIComponent(this.settings.button_width), "&amp;buttonHeight=", encodeURIComponent(this.settings.button_height), "&amp;buttonText=", encodeURIComponent(this.settings.button_text), "&amp;buttonTextTopPadding=", encodeURIComponent(this.settings.button_text_top_padding), "&amp;buttonTextLeftPadding=",
            encodeURIComponent(this.settings.button_text_left_padding), "&amp;buttonTextStyle=", encodeURIComponent(this.settings.button_text_style), "&amp;buttonAction=", encodeURIComponent(this.settings.button_action), "&amp;buttonDisabled=", encodeURIComponent(this.settings.button_disabled), "&amp;buttonCursor=", encodeURIComponent(this.settings.button_cursor)].join("")
    };
    SWFUpload.prototype.getMovieElement = function () {
        if (this.movieElement == void 0)this.movieElement = document.getElementById(this.movieName);
        if (this.movieElement ===
            null)throw"Could not find Flash element";
        return this.movieElement
    };
    SWFUpload.prototype.buildParamString = function () {
        var b = this.settings.post_params, d = [];
        if (typeof b === "object")for (var f in b)b.hasOwnProperty(f) && d.push(encodeURIComponent(f.toString()) + "=" + encodeURIComponent(b[f].toString()));
        return d.join("&amp;")
    };
    SWFUpload.prototype.destroy = function () {
        try {
            this.cancelUpload(null, !1);
            var b = null;
            if ((b = this.getMovieElement()) && typeof b.CallFunction === "unknown") {
                for (var d in b)try {
                    typeof b[d] === "function" &&
                    (b[d] = null)
                } catch (f) {
                }
                try {
                    b.parentNode.removeChild(b)
                } catch (j) {
                }
            }
            window[this.movieName] = null;
            SWFUpload.instances[this.movieName] = null;
            delete SWFUpload.instances[this.movieName];
            this.movieName = this.eventQueue = this.customSettings = this.settings = this.movieElement = null;
            return !0
        } catch (e) {
            return !1
        }
    };
    SWFUpload.prototype.displayDebugInfo = function () {
        this.debug(["---SWFUpload Instance Info---\nVersion: ", SWFUpload.version, "\nMovie Name: ", this.movieName, "\nSettings:\n\tupload_url:               ", this.settings.upload_url,
            "\n\tflash_url:                ", this.settings.flash_url, "\n\tuse_query_string:         ", this.settings.use_query_string.toString(), "\n\trequeue_on_error:         ", this.settings.requeue_on_error.toString(), "\n\thttp_success:             ", this.settings.http_success.join(", "), "\n\tassume_success_timeout:   ", this.settings.assume_success_timeout, "\n\tfile_post_name:           ", this.settings.file_post_name, "\n\tpost_params:              ", this.settings.post_params.toString(), "\n\tfile_types:               ",
            this.settings.file_types, "\n\tfile_types_description:   ", this.settings.file_types_description, "\n\tfile_size_limit:          ", this.settings.file_size_limit, "\n\tfile_upload_limit:        ", this.settings.file_upload_limit, "\n\tfile_queue_limit:         ", this.settings.file_queue_limit, "\n\tdebug:                    ", this.settings.debug.toString(), "\n\tprevent_swf_caching:      ", this.settings.prevent_swf_caching.toString(), "\n\tbutton_placeholder_id:    ", this.settings.button_placeholder_id.toString(),
            "\n\tbutton_placeholder:       ", this.settings.button_placeholder ? "Set" : "Not Set", "\n\tbutton_image_url:         ", this.settings.button_image_url.toString(), "\n\tbutton_width:             ", this.settings.button_width.toString(), "\n\tbutton_height:            ", this.settings.button_height.toString(), "\n\tbutton_text:              ", this.settings.button_text.toString(), "\n\tbutton_text_style:        ", this.settings.button_text_style.toString(), "\n\tbutton_text_top_padding:  ", this.settings.button_text_top_padding.toString(),
            "\n\tbutton_text_left_padding: ", this.settings.button_text_left_padding.toString(), "\n\tbutton_action:            ", this.settings.button_action.toString(), "\n\tbutton_disabled:          ", this.settings.button_disabled.toString(), "\n\tcustom_settings:          ", this.settings.custom_settings.toString(), "\nEvent Handlers:\n\tswfupload_loaded_handler assigned:  ", (typeof this.settings.swfupload_loaded_handler === "function").toString(), "\n\tfile_dialog_start_handler assigned: ", (typeof this.settings.file_dialog_start_handler ===
            "function").toString(), "\n\tfile_queued_handler assigned:       ", (typeof this.settings.file_queued_handler === "function").toString(), "\n\tfile_queue_error_handler assigned:  ", (typeof this.settings.file_queue_error_handler === "function").toString(), "\n\tupload_start_handler assigned:      ", (typeof this.settings.upload_start_handler === "function").toString(), "\n\tupload_progress_handler assigned:   ", (typeof this.settings.upload_progress_handler === "function").toString(), "\n\tupload_error_handler assigned:      ",
            (typeof this.settings.upload_error_handler === "function").toString(), "\n\tupload_success_handler assigned:    ", (typeof this.settings.upload_success_handler === "function").toString(), "\n\tupload_complete_handler assigned:   ", (typeof this.settings.upload_complete_handler === "function").toString(), "\n\tdebug_handler assigned:             ", (typeof this.settings.debug_handler === "function").toString(), "\n"].join(""))
    };
    SWFUpload.prototype.addSetting = function (b, d, f) {
        return d == void 0 ? this.settings[b] = f : this.settings[b] =
            d
    };
    SWFUpload.prototype.getSetting = function (b) {
        if (this.settings[b] != void 0)return this.settings[b];
        return ""
    };
    SWFUpload.prototype.callFlash = function (b, d) {
        var d = d || [], f = this.getMovieElement(), j, e;
        try {
            e = f.CallFunction('<invoke name="' + b + '" returntype="javascript">' + __flash__argumentsToXML(d, 0) + "</invoke>"), j = eval(e)
        } catch (h) {
            throw"Call to " + b + " failed";
        }
        j != void 0 && typeof j.post === "object" && (j = this.unescapeFilePostParams(j));
        return j
    };
    SWFUpload.prototype.selectFile = function () {
        this.callFlash("SelectFile")
    };
    SWFUpload.prototype.selectFiles = function () {
        this.callFlash("SelectFiles")
    };
    SWFUpload.prototype.startUpload = function (b) {
        this.callFlash("StartUpload", [b])
    };
    SWFUpload.prototype.cancelUpload = function (b, d) {
        d !== !1 && (d = !0);
        this.callFlash("CancelUpload", [b, d])
    };
    SWFUpload.prototype.stopUpload = function () {
        this.callFlash("StopUpload")
    };
    SWFUpload.prototype.getStats = function () {
        return this.callFlash("GetStats")
    };
    SWFUpload.prototype.setStats = function (b) {
        this.callFlash("SetStats", [b])
    };
    SWFUpload.prototype.getFile =
        function (b) {
            return typeof b === "number" ? this.callFlash("GetFileByIndex", [b]) : this.callFlash("GetFile", [b])
        };
    SWFUpload.prototype.addFileParam = function (b, d, f) {
        return this.callFlash("AddFileParam", [b, d, f])
    };
    SWFUpload.prototype.removeFileParam = function (b, d) {
        this.callFlash("RemoveFileParam", [b, d])
    };
    SWFUpload.prototype.setUploadURL = function (b) {
        this.settings.upload_url = b.toString();
        this.callFlash("SetUploadURL", [b])
    };
    SWFUpload.prototype.setPostParams = function (b) {
        this.settings.post_params = b;
        this.callFlash("SetPostParams",
            [b])
    };
    SWFUpload.prototype.addPostParam = function (b, d) {
        this.settings.post_params[b] = d;
        this.callFlash("SetPostParams", [this.settings.post_params])
    };
    SWFUpload.prototype.removePostParam = function (b) {
        delete this.settings.post_params[b];
        this.callFlash("SetPostParams", [this.settings.post_params])
    };
    SWFUpload.prototype.setFileTypes = function (b, d) {
        this.settings.file_types = b;
        this.settings.file_types_description = d;
        this.callFlash("SetFileTypes", [b, d])
    };
    SWFUpload.prototype.setFileSizeLimit = function (b) {
        this.settings.file_size_limit =
            b;
        this.callFlash("SetFileSizeLimit", [b])
    };
    SWFUpload.prototype.setFileUploadLimit = function (b) {
        this.settings.file_upload_limit = b;
        this.callFlash("SetFileUploadLimit", [b])
    };
    SWFUpload.prototype.setFileQueueLimit = function (b) {
        this.settings.file_queue_limit = b;
        this.callFlash("SetFileQueueLimit", [b])
    };
    SWFUpload.prototype.setFilePostName = function (b) {
        this.settings.file_post_name = b;
        this.callFlash("SetFilePostName", [b])
    };
    SWFUpload.prototype.setUseQueryString = function (b) {
        this.settings.use_query_string = b;
        this.callFlash("SetUseQueryString",
            [b])
    };
    SWFUpload.prototype.setRequeueOnError = function (b) {
        this.settings.requeue_on_error = b;
        this.callFlash("SetRequeueOnError", [b])
    };
    SWFUpload.prototype.setHTTPSuccess = function (b) {
        typeof b === "string" && (b = b.replace(" ", "").split(","));
        this.settings.http_success = b;
        this.callFlash("SetHTTPSuccess", [b])
    };
    SWFUpload.prototype.setAssumeSuccessTimeout = function (b) {
        this.settings.assume_success_timeout = b;
        this.callFlash("SetAssumeSuccessTimeout", [b])
    };
    SWFUpload.prototype.setDebugEnabled = function (b) {
        this.settings.debug_enabled =
            b;
        this.callFlash("SetDebugEnabled", [b])
    };
    SWFUpload.prototype.setButtonImageURL = function (b) {
        b == void 0 && (b = "");
        this.settings.button_image_url = b;
        this.callFlash("SetButtonImageURL", [b])
    };
    SWFUpload.prototype.setButtonDimensions = function (b, d) {
        this.settings.button_width = b;
        this.settings.button_height = d;
        var f = this.getMovieElement();
        if (f != void 0)f.style.width = b + "px", f.style.height = d + "px";
        this.callFlash("SetButtonDimensions", [b, d])
    };
    SWFUpload.prototype.setButtonText = function (b) {
        this.settings.button_text = b;
        this.callFlash("SetButtonText",
            [b])
    };
    SWFUpload.prototype.setButtonTextPadding = function (b, d) {
        this.settings.button_text_top_padding = d;
        this.settings.button_text_left_padding = b;
        this.callFlash("SetButtonTextPadding", [b, d])
    };
    SWFUpload.prototype.setButtonTextStyle = function (b) {
        this.settings.button_text_style = b;
        this.callFlash("SetButtonTextStyle", [b])
    };
    SWFUpload.prototype.setButtonDisabled = function (b) {
        this.settings.button_disabled = b;
        this.callFlash("SetButtonDisabled", [b])
    };
    SWFUpload.prototype.setButtonAction = function (b) {
        this.settings.button_action =
            b;
        this.callFlash("SetButtonAction", [b])
    };
    SWFUpload.prototype.setButtonCursor = function (b) {
        this.settings.button_cursor = b;
        this.callFlash("SetButtonCursor", [b])
    };
    SWFUpload.prototype.queueEvent = function (b, d) {
        d == void 0 ? d = [] : d instanceof Array || (d = [d]);
        var f = this;
        if (typeof this.settings[b] === "function")this.eventQueue.push(function () {
            this.settings[b].apply(this, d)
        }), setTimeout(function () {
            f.executeNextEvent()
        }, 0); else if (this.settings[b] !== null)throw"Event handler " + b + " is unknown or is not a function";
    };
    SWFUpload.prototype.executeNextEvent = function () {
        var b = this.eventQueue ? this.eventQueue.shift() : null;
        typeof b === "function" && b.apply(this)
    };
    SWFUpload.prototype.unescapeFilePostParams = function (b) {
        var d = /[$]([0-9a-f]{4})/i, f = {}, j;
        if (b != void 0) {
            for (var e in b.post)if (b.post.hasOwnProperty(e)) {
                j = e;
                for (var h; (h = d.exec(j)) !== null;)j = j.replace(h[0], String.fromCharCode(parseInt("0x" + h[1], 16)));
                f[j] = b.post[e]
            }
            b.post = f
        }
        return b
    };
    SWFUpload.prototype.testExternalInterface = function () {
        try {
            return this.callFlash("TestExternalInterface")
        } catch (b) {
            return !1
        }
    };
    SWFUpload.prototype.flashReady = function () {
        var b = this.getMovieElement();
        b ? (this.cleanUp(b), this.queueEvent("swfupload_loaded_handler")) : this.debug("Flash called back ready but the flash movie can't be found.")
    };
    SWFUpload.prototype.cleanUp = function (b) {
        try {
            if (this.movieElement && typeof b.CallFunction === "unknown") {
                this.debug("Removing Flash functions hooks (this should only run in IE and should prevent memory leaks)");
                for (var d in b)try {
                    typeof b[d] === "function" && (b[d] = null)
                } catch (f) {
                }
            }
        } catch (j) {
        }
        window.__flash__removeCallback =
            function (b, d) {
                try {
                    b && (b[d] = null)
                } catch (f) {
                }
            }
    };
    SWFUpload.prototype.fileDialogStart = function () {
        this.queueEvent("file_dialog_start_handler")
    };
    SWFUpload.prototype.fileQueued = function (b) {
        b = this.unescapeFilePostParams(b);
        this.queueEvent("file_queued_handler", b)
    };
    SWFUpload.prototype.fileQueueError = function (b, d, f) {
        b = this.unescapeFilePostParams(b);
        this.queueEvent("file_queue_error_handler", [b, d, f])
    };
    SWFUpload.prototype.fileDialogComplete = function (b, d, f) {
        this.queueEvent("file_dialog_complete_handler", [b, d,
            f])
    };
    SWFUpload.prototype.uploadStart = function (b) {
        b = this.unescapeFilePostParams(b);
        this.queueEvent("return_upload_start_handler", b)
    };
    SWFUpload.prototype.returnUploadStart = function (b) {
        var d;
        if (typeof this.settings.upload_start_handler === "function")b = this.unescapeFilePostParams(b), d = this.settings.upload_start_handler.call(this, b); else if (this.settings.upload_start_handler != void 0)throw"upload_start_handler must be a function";
        d === void 0 && (d = !0);
        this.callFlash("ReturnUploadStart", [!!d])
    };
    SWFUpload.prototype.uploadProgress =
        function (b, d, f) {
            b = this.unescapeFilePostParams(b);
            this.queueEvent("upload_progress_handler", [b, d, f])
        };
    SWFUpload.prototype.uploadError = function (b, d, f) {
        b = this.unescapeFilePostParams(b);
        this.queueEvent("upload_error_handler", [b, d, f])
    };
    SWFUpload.prototype.uploadSuccess = function (b, d, f) {
        b = this.unescapeFilePostParams(b);
        this.queueEvent("upload_success_handler", [b, d, f])
    };
    SWFUpload.prototype.uploadComplete = function (b) {
        b = this.unescapeFilePostParams(b);
        this.queueEvent("upload_complete_handler", b)
    };
    SWFUpload.prototype.debug =
        function (b) {
            this.queueEvent("debug_handler", b)
        };
    SWFUpload.prototype.debugMessage = function (b) {
        if (this.settings.debug) {
            var d = [];
            if (typeof b === "object" && typeof b.name === "string" && typeof b.message === "string") {
                for (var f in b)b.hasOwnProperty(f) && d.push(f + ": " + b[f]);
                b = d.join("\n") || "";
                d = b.split("\n");
                b = "EXCEPTION: " + d.join("\nEXCEPTION: ")
            }
            SWFUpload.Console.writeLine(b)
        }
    };
    SWFUpload.Console = {};
    SWFUpload.Console.writeLine = function (b) {
        var d, f;
        try {
            d = document.getElementById("SWFUpload_Console");
            if (!d)f = document.createElement("form"),
                document.getElementsByTagName("body")[0].appendChild(f), d = document.createElement("textarea"), d.id = "SWFUpload_Console", d.style.fontFamily = "monospace", d.setAttribute("wrap", "off"), d.wrap = "off", d.style.overflow = "auto", d.style.width = "700px", d.style.height = "350px", d.style.margin = "5px", f.appendChild(d);
            d.value += b + "\n";
            d.scrollTop = d.scrollHeight - d.clientHeight
        } catch (j) {
            alert("Exception: " + j.name + " Message: " + j.message)
        }
    }
})();
(function () {
    if (typeof SWFUpload === "function")SWFUpload.queue = {}, SWFUpload.prototype.initSettings = function (b) {
        return function () {
            typeof b === "function" && b.call(this);
            this.queueSettings = {};
            this.queueSettings.queue_cancelled_flag = !1;
            this.queueSettings.queue_upload_count = 0;
            this.queueSettings.user_upload_complete_handler = this.settings.upload_complete_handler;
            this.queueSettings.user_upload_start_handler = this.settings.upload_start_handler;
            this.settings.upload_complete_handler = SWFUpload.queue.uploadCompleteHandler;
            this.settings.upload_start_handler = SWFUpload.queue.uploadStartHandler;
            this.settings.queue_complete_handler = this.settings.queue_complete_handler || null
        }
    }(SWFUpload.prototype.initSettings), SWFUpload.prototype.startUpload = function (b) {
        this.queueSettings.queue_cancelled_flag = !1;
        this.callFlash("StartUpload", [b])
    }, SWFUpload.prototype.cancelQueue = function () {
        this.queueSettings.queue_cancelled_flag = !0;
        this.stopUpload();
        for (var b = this.getStats(); b.files_queued > 0;)this.cancelUpload(), b = this.getStats()
    }, SWFUpload.queue.uploadStartHandler =
        function (b) {
            var d;
            typeof this.queueSettings.user_upload_start_handler === "function" && (d = this.queueSettings.user_upload_start_handler.call(this, b));
            d = d === !1 ? !1 : !0;
            this.queueSettings.queue_cancelled_flag = !d;
            return d
        }, SWFUpload.queue.uploadCompleteHandler = function (b) {
        var d = this.queueSettings.user_upload_complete_handler;
        b.filestatus === SWFUpload.FILE_STATUS.COMPLETE && this.queueSettings.queue_upload_count++;
        if (typeof d === "function" ? d.call(this, b) !== !1 : b.filestatus !== SWFUpload.FILE_STATUS.QUEUED)this.getStats().files_queued >
        0 && this.queueSettings.queue_cancelled_flag === !1 ? this.startUpload() : (this.queueSettings.queue_cancelled_flag === !1 ? this.queueEvent("queue_complete_handler", [this.queueSettings.queue_upload_count]) : this.queueSettings.queue_cancelled_flag = !1, this.queueSettings.queue_upload_count = 0)
    }
})();
KindEditor.plugin("pagebreak", function (b) {
    var d = this, f = b.undef(d.pagebreakHtml, '<hr style="page-break-after: always;" class="ke-pagebreak" />');
    d.clickToolbar("pagebreak", function () {
        var j = d.cmd, e = j.range;
        d.focus();
        var h = d.newlineTag == "br" || b.WEBKIT ? "" : '<span id="__kindeditor_tail_tag__"></span>';
        d.insertHtml(f + h);
        h !== "" && (h = b("#__kindeditor_tail_tag__", d.edit.doc), e.selectNodeContents(h[0]), h.removeAttr("id"), j.select())
    })
});
KindEditor.plugin("plainpaste", function (b) {
    var d = this;
    d.clickToolbar("plainpaste", function () {
        var f = '<div style="padding:10px 20px;"><div style="margin-bottom:10px;">' + d.lang("plainpaste.").comment + '</div><textarea class="ke-textarea" style="width:408px;height:260px;"></textarea></div>', f = d.createDialog({
            name: "plainpaste", width: 450, title: d.lang("plainpaste"), body: f, yesBtn: {
                name: d.lang("yes"), click: function () {
                    var e = j.val(), e = b.escape(e), e = e.replace(/ {2}/g, " &nbsp;"), e = d.newlineTag == "p" ? e.replace(/^/,
                        "<p>").replace(/$/, "</p>").replace(/\n/g, "</p><p>") : e.replace(/\n/g, "<br />$&");
                    d.insertHtml(e).hideDialog().focus()
                }
            }
        }), j = b("textarea", f.div);
        j[0].focus()
    })
});
KindEditor.plugin("preview", function (b) {
    var d = this;
    d.clickToolbar("preview", function () {
        d.lang("preview.");
        var f = d.createDialog({
            name: "preview",
            width: 750,
            title: d.lang("preview"),
            body: '<div style="padding:10px 20px;"><iframe class="ke-textarea" frameborder="0" style="width:708px;height:400px;"></iframe></div>'
        }), f = b("iframe", f.div), j = b.iframeDoc(f);
        j.open();
        j.write(d.fullHtml());
        j.close();
        b(j.body).css("background-color", "#FFF");
        f[0].contentWindow.focus()
    })
});
KindEditor.plugin("quickformat", function (b) {
    function d(b) {
        for (b = b.first(); b && b.first();)b = b.first();
        return b
    }

    var f = this, j = b.toMap("blockquote,center,div,h1,h2,h3,h4,h5,h6,p");
    f.clickToolbar("quickformat", function () {
        f.focus();
        for (var e = f.edit.doc, h = f.cmd.range, m = b(e.body).first(), n, o = [], l = [], s = h.createBookmark(!0); m;) {
            n = m.next();
            var v = d(m);
            if (!v || v.name != "img")if (j[m.name] ? (m.html(m.html().replace(/^(\s|&nbsp;|\u3000)+/ig, "")), m.css("text-indent", "2em")) : l.push(m), !n || j[n.name] || j[m.name] && !j[n.name])l.length >
            0 && o.push(l), l = [];
            m = n
        }
        b.each(o, function (d, f) {
            var h = b('<p style="text-indent:2em;"></p>', e);
            f[0].before(h);
            b.each(f, function (b, d) {
                h.append(d)
            })
        });
        h.moveToBookmark(s);
        f.addBookmark()
    })
});
KindEditor.plugin("table", function (b) {
    function d(b, d) {
        d = d.toUpperCase();
        b.css("background-color", d);
        b.css("color", d === "#000000" ? "#FFFFFF" : "#000000");
        b.html(d)
    }

    function f(f, h) {
        function l() {
            b.each(m, function () {
                this.remove()
            });
            m = [];
            b(document).unbind("click,mousedown", l);
            f.unbind("click,mousedown", l)
        }

        h.bind("click,mousedown", function (b) {
            b.stopPropagation()
        });
        h.click(function () {
            l();
            var h = b(this), o = h.pos(), o = b.colorpicker({
                x: o.x, y: o.y + h.height(), z: 811214, selectedColor: b(this).html(), colors: e.colorTable,
                noColor: e.lang("noColor"), shadowMode: e.shadowMode, click: function (b) {
                    d(h, b);
                    l()
                }
            });
            m.push(o);
            b(document).bind("click,mousedown", l);
            f.bind("click,mousedown", l)
        })
    }

    function j(b, d, e) {
        for (var f = b = 0, h = d.cells.length; f < h; f++) {
            if (d.cells[f] == e)break;
            b += d.cells[f].rowSpan - 1
        }
        return e.cellIndex - b
    }

    var e = this, h = e.lang("table."), m = [];
    e.plugin.table = {
        prop: function (n) {
            var o = ['<div style="padding:20px;"><div class="ke-dialog-row">', '<label for="keRows" style="width:90px;">' + h.cells + "</label>", h.rows + ' <input type="text" id="keRows" class="ke-input-text ke-input-number" name="rows" value="" maxlength="4" /> &nbsp; ',
                h.cols + ' <input type="text" class="ke-input-text ke-input-number" name="cols" value="" maxlength="4" />', '</div><div class="ke-dialog-row">', '<label for="keWidth" style="width:90px;">' + h.size + "</label>", h.width + ' <input type="text" id="keWidth" class="ke-input-text ke-input-number" name="width" value="" maxlength="4" /> &nbsp; ', '<select name="widthType">', '<option value="%">' + h.percent + "</option>", '<option value="px">' + h.px + "</option>", "</select> &nbsp; ", h.height + ' <input type="text" class="ke-input-text ke-input-number" name="height" value="" maxlength="4" /> &nbsp; ',
                '<select name="heightType">', '<option value="%">' + h.percent + "</option>", '<option value="px">' + h.px + "</option>", '</select></div><div class="ke-dialog-row">', '<label for="kePadding" style="width:90px;">' + h.space + "</label>", h.padding + ' <input type="text" id="kePadding" class="ke-input-text ke-input-number" name="padding" value="" maxlength="4" /> &nbsp; ', h.spacing + ' <input type="text" class="ke-input-text ke-input-number" name="spacing" value="" maxlength="4" />', '</div><div class="ke-dialog-row">',
                '<label for="keAlign" style="width:90px;">' + h.align + "</label>", '<select id="keAlign" name="align">', '<option value="">' + h.alignDefault + "</option>", '<option value="left">' + h.alignLeft + "</option>", '<option value="center">' + h.alignCenter + "</option>", '<option value="right">' + h.alignRight + "</option>", '</select></div><div class="ke-dialog-row">', '<label for="keBorder" style="width:90px;">' + h.border + "</label>", h.borderWidth + ' <input type="text" id="keBorder" class="ke-input-text ke-input-number" name="border" value="" maxlength="4" /> &nbsp; ',
                h.borderColor + ' <span class="ke-inline-block ke-input-color"></span>', '</div><div class="ke-dialog-row">', '<label for="keBgColor" style="width:90px;">' + h.backgroundColor + "</label>", '<span class="ke-inline-block ke-input-color"></span></div></div>'].join(""), l = e.cmd.range.createBookmark(), o = e.createDialog({
                name: "table", width: 500, title: e.lang("table"), body: o, beforeRemove: function () {
                    C.unbind()
                }, yesBtn: {
                    name: e.lang("yes"), click: function () {
                        var d = j.val(), f = m.val(), h = p.val(), n = r.val(), o = z.val(), I = D.val(),
                            O = q.val(), Q = A.val(), R = B.val(), H = G.val(), U = b(C[0]).html() || "", W = b(C[1]).html() || "";
                        if (d == 0 || !/^\d+$/.test(d))alert(e.lang("invalidRows")), j[0].focus(); else if (f == 0 || !/^\d+$/.test(f))alert(e.lang("invalidRows")), m[0].focus(); else if (/^\d*$/.test(h))if (/^\d*$/.test(n))if (/^\d*$/.test(O))if (/^\d*$/.test(Q))if (/^\d*$/.test(H)) {
                            if (u)h !== "" ? u.width(h + o) : u.css("width", ""), u[0].width !== void 0 && u.removeAttr("width"), n !== "" ? u.height(n + I) : u.css("height", ""), u[0].height !== void 0 && u.removeAttr("height"), u.css("background-color",
                                W), u[0].bgColor !== void 0 && u.removeAttr("bgColor"), O !== "" ? u[0].cellPadding = O : u.removeAttr("cellPadding"), Q !== "" ? u[0].cellSpacing = Q : u.removeAttr("cellSpacing"), R !== "" ? u[0].align = R : u.removeAttr("align"), H !== "" ? u.attr("border", H) : u.removeAttr("border"), H === "" || H === "0" ? u.addClass("ke-zeroborder") : u.removeClass("ke-zeroborder"), U !== "" ? u.attr("borderColor", U) : u.removeAttr("borderColor"), e.hideDialog().focus(), e.cmd.range.moveToBookmark(l), e.cmd.select(); else {
                                var S = "";
                                h !== "" && (S += "width:" + h + o + ";");
                                n !== "" &&
                                (S += "height:" + n + I + ";");
                                W !== "" && (S += "background-color:" + W + ";");
                                h = "<table";
                                S !== "" && (h += ' style="' + S + '"');
                                O !== "" && (h += ' cellpadding="' + O + '"');
                                Q !== "" && (h += ' cellspacing="' + Q + '"');
                                R !== "" && (h += ' align="' + R + '"');
                                H !== "" && (h += ' border="' + H + '"');
                                if (H === "" || H === "0")h += ' class="ke-zeroborder"';
                                U !== "" && (h += ' bordercolor="' + U + '"');
                                h += ">";
                                for (O = 0; O < d; O++) {
                                    h += "<tr>";
                                    for (Q = 0; Q < f; Q++)h += "<td>" + (b.IE ? "&nbsp;" : "<br />") + "</td>";
                                    h += "</tr>"
                                }
                                h += "</table>";
                                b.IE || (h += "<br />");
                                e.insertHtml(h);
                                e.select().hideDialog().focus()
                            }
                            e.addBookmark()
                        } else alert(e.lang("invalidBorder")),
                            G[0].focus(); else alert(e.lang("invalidSpacing")), A[0].focus(); else alert(e.lang("invalidPadding")), q[0].focus(); else alert(e.lang("invalidHeight")), r[0].focus(); else alert(e.lang("invalidWidth")), p[0].focus()
                    }
                }
            }).div, j = b('[name="rows"]', o).val(3), m = b('[name="cols"]', o).val(2), p = b('[name="width"]', o).val(100), r = b('[name="height"]', o), z = b('[name="widthType"]', o), D = b('[name="heightType"]', o), q = b('[name="padding"]', o).val(2), A = b('[name="spacing"]', o).val(0), B = b('[name="align"]', o), G = b('[name="border"]',
                o).val(1), C = b(".ke-input-color", o);
            f(o, C.eq(0));
            f(o, C.eq(1));
            d(C.eq(0), "#000000");
            d(C.eq(1), "");
            j[0].focus();
            j[0].select();
            var u;
            if (!n && (u = e.plugin.getSelectedTable())) {
                j.val(u[0].rows.length);
                m.val(u[0].rows.length > 0 ? u[0].rows[0].cells.length : 0);
                j.attr("disabled", !0);
                m.attr("disabled", !0);
                var I, n = u[0].style.width || u[0].width, o = u[0].style.height || u[0].height;
                n !== void 0 && (I = /^(\d+)((?:px|%)*)$/.exec(n)) ? (p.val(I[1]), z.val(I[2])) : p.val("");
                if (o !== void 0 && (I = /^(\d+)((?:px|%)*)$/.exec(o)))r.val(I[1]),
                    D.val(I[2]);
                q.val(u[0].cellPadding || "");
                A.val(u[0].cellSpacing || "");
                B.val(u[0].align || "");
                G.val(u[0].border === void 0 ? "" : u[0].border);
                d(C.eq(0), b.toHex(u.attr("borderColor") || ""));
                d(C.eq(1), b.toHex(u[0].style.backgroundColor || u[0].bgColor || ""));
                p[0].focus();
                p[0].select()
            }
        }, cellprop: function () {
            var n = ['<div style="padding:20px;"><div class="ke-dialog-row">', '<label for="keWidth" style="width:90px;">' + h.size + "</label>", h.width + ' <input type="text" id="keWidth" class="ke-input-text ke-input-number" name="width" value="" maxlength="4" /> &nbsp; ',
                    '<select name="widthType">', '<option value="%">' + h.percent + "</option>", '<option value="px">' + h.px + "</option>", "</select> &nbsp; ", h.height + ' <input type="text" class="ke-input-text ke-input-number" name="height" value="" maxlength="4" /> &nbsp; ', '<select name="heightType">', '<option value="%">' + h.percent + "</option>", '<option value="px">' + h.px + "</option>", '</select></div><div class="ke-dialog-row">', '<label for="keAlign" style="width:90px;">' + h.align + "</label>", h.textAlign + ' <select id="keAlign" name="textAlign">',
                    '<option value="">' + h.alignDefault + "</option>", '<option value="left">' + h.alignLeft + "</option>", '<option value="center">' + h.alignCenter + "</option>", '<option value="right">' + h.alignRight + "</option>", "</select> ", h.verticalAlign + ' <select name="verticalAlign">', '<option value="">' + h.alignDefault + "</option>", '<option value="top">' + h.alignTop + "</option>", '<option value="middle">' + h.alignMiddle + "</option>", '<option value="bottom">' + h.alignBottom + "</option>", '<option value="baseline">' + h.alignBaseline + "</option>",
                    '</select></div><div class="ke-dialog-row">', '<label for="keBorder" style="width:90px;">' + h.border + "</label>", h.borderWidth + ' <input type="text" id="keBorder" class="ke-input-text ke-input-number" name="border" value="" maxlength="4" /> &nbsp; ', h.borderColor + ' <span class="ke-inline-block ke-input-color"></span>', '</div><div class="ke-dialog-row">', '<label for="keBgColor" style="width:90px;">' + h.backgroundColor + "</label>", '<span class="ke-inline-block ke-input-color"></span></div></div>'].join(""),
                o = e.cmd.range.createBookmark(), n = e.createDialog({
                    name: "table", width: 500, title: e.lang("tablecell"), body: n, beforeRemove: function () {
                        B.unbind()
                    }, yesBtn: {
                        name: e.lang("yes"), click: function () {
                            var d = l.val(), f = j.val(), h = m.val(), n = p.val();
                            r.val();
                            z.val();
                            var C = D.val(), $ = q.val(), M = A.val(), K = b(B[0]).html() || "", O = b(B[1]).html() || "";
                            /^\d*$/.test(d) ? /^\d*$/.test(f) ? /^\d*$/.test(M) ? (G.css({
                                width: d !== "" ? d + h : "",
                                height: f !== "" ? f + n : "",
                                "background-color": O,
                                "text-align": C,
                                "vertical-align": $,
                                "border-width": M,
                                "border-style": M !==
                                "" ? "solid" : "",
                                "border-color": K
                            }), e.hideDialog().focus(), e.cmd.range.moveToBookmark(o), e.cmd.select(), e.addBookmark()) : (alert(e.lang("invalidBorder")), A[0].focus()) : (alert(e.lang("invalidHeight")), j[0].focus()) : (alert(e.lang("invalidWidth")), l[0].focus())
                        }
                    }
                }).div, l = b('[name="width"]', n).val(100), j = b('[name="height"]', n), m = b('[name="widthType"]', n), p = b('[name="heightType"]', n), r = b('[name="padding"]', n).val(2), z = b('[name="spacing"]', n).val(0), D = b('[name="textAlign"]', n), q = b('[name="verticalAlign"]',
                    n), A = b('[name="border"]', n).val(1), B = b(".ke-input-color", n);
            f(n, B.eq(0));
            f(n, B.eq(1));
            d(B.eq(0), "#000000");
            d(B.eq(1), "");
            l[0].focus();
            l[0].select();
            var G = e.plugin.getSelectedCell(), C = G[0].style.height || G[0].height || "";
            (n = /^(\d+)((?:px|%)*)$/.exec(G[0].style.width || G[0].width || "")) ? (l.val(n[1]), m.val(n[2])) : l.val("");
            if (n = /^(\d+)((?:px|%)*)$/.exec(C))j.val(n[1]), p.val(n[2]);
            D.val(G[0].style.textAlign || "");
            q.val(G[0].style.verticalAlign || "");
            (n = G[0].style.borderWidth || "") && (n = parseInt(n));
            A.val(n);
            d(B.eq(0), b.toHex(G[0].style.borderColor || ""));
            d(B.eq(1), b.toHex(G[0].style.backgroundColor || ""));
            l[0].focus();
            l[0].select()
        }, insert: function () {
            this.prop(!0)
        }, "delete": function () {
            var b = e.plugin.getSelectedTable();
            e.cmd.range.setStartBefore(b[0]).collapse(!0);
            e.cmd.select();
            b.remove();
            e.addBookmark()
        }, colinsert: function (d) {
            var f = e.plugin.getSelectedTable()[0], h = e.plugin.getSelectedRow()[0], m = e.plugin.getSelectedCell()[0], d = m.cellIndex + d;
            d += f.rows[0].cells.length - h.cells.length;
            for (var h = 0, v = f.rows.length; h <
            v; h++) {
                var p = f.rows[h], d = p.insertCell(d);
                d.innerHTML = b.IE ? "" : "<br />";
                d = j(f, p, d)
            }
            e.cmd.range.selectNodeContents(m).collapse(!0);
            e.cmd.select();
            e.addBookmark()
        }, colinsertleft: function () {
            this.colinsert(0)
        }, colinsertright: function () {
            this.colinsert(1)
        }, rowinsert: function (d) {
            var f = e.plugin.getSelectedTable()[0], h = e.plugin.getSelectedRow()[0], j = e.plugin.getSelectedCell()[0], m = h.rowIndex;
            d === 1 && (m = h.rowIndex + (j.rowSpan - 1) + d);
            for (var p = f.insertRow(m), r = 0, z = h.cells.length; r < z; r++) {
                h.cells[r].rowSpan > 1 && (z -=
                    h.cells[r].rowSpan - 1);
                var D = p.insertCell(r);
                if (d === 1 && h.cells[r].colSpan > 1)D.colSpan = h.cells[r].colSpan;
                D.innerHTML = b.IE ? "" : "<br />"
            }
            for (h = m; h >= 0; h--)if (d = f.rows[h].cells, d.length > r) {
                for (f = j.cellIndex; f >= 0; f--)d[f].rowSpan > 1 && (d[f].rowSpan += 1);
                break
            }
            e.cmd.range.selectNodeContents(j).collapse(!0);
            e.cmd.select();
            e.addBookmark()
        }, rowinsertabove: function () {
            this.rowinsert(0)
        }, rowinsertbelow: function () {
            this.rowinsert(1)
        }, rowmerge: function () {
            var b = e.plugin.getSelectedTable()[0], d = e.plugin.getSelectedRow()[0],
                f = e.plugin.getSelectedCell()[0], h = d.rowIndex + f.rowSpan, d = b.rows[h];
            if (!(b.rows.length <= h))b = f.cellIndex, d.cells.length <= b || (h = d.cells[b], f.colSpan === h.colSpan && (f.rowSpan += h.rowSpan, d.deleteCell(b), e.cmd.range.selectNodeContents(f).collapse(!0), e.cmd.select(), e.addBookmark()))
        }, colmerge: function () {
            e.plugin.getSelectedTable();
            var b = e.plugin.getSelectedRow()[0], d = e.plugin.getSelectedCell()[0], f = d.cellIndex + 1;
            if (!(b.cells.length <= f)) {
                var h = b.cells[f];
                d.rowSpan === h.rowSpan && (d.colSpan += h.colSpan, b.deleteCell(f),
                    e.cmd.range.selectNodeContents(d).collapse(!0), e.cmd.select(), e.addBookmark())
            }
        }, rowsplit: function () {
            var d = e.plugin.getSelectedTable()[0], f = e.plugin.getSelectedRow()[0], h = e.plugin.getSelectedCell()[0], m = f.rowIndex;
            if (h.rowSpan !== 1) {
                for (var v = j(d, f, h), f = 1, p = h.rowSpan; f < p; f++) {
                    var r = d.rows[m + f], v = r.insertCell(v);
                    if (h.colSpan > 1)v.colSpan = h.colSpan;
                    v.innerHTML = b.IE ? "" : "<br />";
                    v = j(d, r, v)
                }
                b(h).removeAttr("rowSpan");
                e.cmd.range.selectNodeContents(h).collapse(!0);
                e.cmd.select();
                e.addBookmark()
            }
        }, colsplit: function () {
            e.plugin.getSelectedTable();
            var d = e.plugin.getSelectedRow()[0], f = e.plugin.getSelectedCell()[0], h = f.cellIndex;
            if (f.colSpan !== 1) {
                for (var j = 1, m = f.colSpan; j < m; j++) {
                    var p = d.insertCell(h + j);
                    if (f.rowSpan > 1)p.rowSpan = f.rowSpan;
                    p.innerHTML = b.IE ? "" : "<br />"
                }
                b(f).removeAttr("colSpan");
                e.cmd.range.selectNodeContents(f).collapse(!0);
                e.cmd.select();
                e.addBookmark()
            }
        }, coldelete: function () {
            for (var d = e.plugin.getSelectedTable()[0], f = e.plugin.getSelectedRow()[0], h = e.plugin.getSelectedCell()[0].cellIndex, j = 0, m = d.rows.length; j < m; j++) {
                var p = d.rows[j],
                    r = p.cells[h];
                r.colSpan > 1 ? (r.colSpan -= 1, r.colSpan === 1 && b(r).removeAttr("colSpan")) : p.deleteCell(h);
                r.rowSpan > 1 && (j += r.rowSpan - 1)
            }
            f.cells.length === 0 ? (e.cmd.range.setStartBefore(d).collapse(!0), e.cmd.select(), b(d).remove()) : e.cmd.selection(!0);
            e.addBookmark()
        }, rowdelete: function () {
            for (var d = e.plugin.getSelectedTable()[0], f = e.plugin.getSelectedRow()[0], h = e.plugin.getSelectedCell()[0], f = f.rowIndex, h = h.rowSpan - 1; h >= 0; h--)d.deleteRow(f + h);
            d.rows.length === 0 ? (e.cmd.range.setStartBefore(d).collapse(!0), e.cmd.select(),
                b(d).remove()) : e.cmd.selection(!0);
            e.addBookmark()
        }
    };
    e.clickToolbar("table", e.plugin.table.prop)
});
KindEditor.plugin("template", function (b) {
    function d(d) {
        return j + d + "?ver=" + encodeURIComponent(b.DEBUG ? b.TIME : b.VERSION)
    }

    var f = this;
    f.lang("template.");
    var j = f.pluginsPath + "template/html/";
    f.clickToolbar("template", function () {
        var e = f.lang("template."), h = ['<div style="padding:10px 20px;">', '<div class="ke-header">', '<div class="ke-left">', e.selectTemplate + " <select>"];
        b.each(e.fileList, function (b, d) {
            h.push('<option value="' + b + '">' + d + "</option>")
        });
        html = [h.join(""), '</select></div><div class="ke-right">',
            '<input type="checkbox" id="keReplaceFlag" name="replaceFlag" value="1" /> <label for="keReplaceFlag">' + e.replaceContent + "</label>", '</div><div class="ke-clearfix"></div></div><iframe class="ke-textarea" frameborder="0" style="width:458px;height:260px;background-color:#FFF;"></iframe></div>'].join("");
        var e = f.createDialog({
                name: "template", width: 500, title: f.lang("template"), body: html, yesBtn: {
                    name: f.lang("yes"), click: function () {
                        var d = b.iframeDoc(o);
                        f[n[0].checked ? "html" : "insertHtml"](d.body.innerHTML).hideDialog().focus()
                    }
                }
            }),
            j = b("select", e.div), n = b('[name="replaceFlag"]', e.div), o = b("iframe", e.div);
        n[0].checked = !0;
        o.attr("src", d(j.val()));
        j.change(function () {
            o.attr("src", d(this.value))
        })
    })
});
KindEditor.plugin("wordpaste", function (b) {
    var d = this;
    d.clickToolbar("wordpaste", function () {
        var f = '<div style="padding:10px 20px;"><div style="margin-bottom:10px;">' + d.lang("wordpaste.").comment + '</div><iframe class="ke-textarea" frameborder="0" style="width:408px;height:260px;"></iframe></div>', f = d.createDialog({
            name: "wordpaste", width: 450, title: d.lang("wordpaste"), body: f, yesBtn: {
                name: d.lang("yes"), click: function () {
                    var e = j.body.innerHTML, e = b.clearMsWord(e, d.filterMode ? d.htmlTags : b.options.htmlTags);
                    d.insertHtml(e).hideDialog().focus()
                }
            }
        }).div, f = b("iframe", f), j = b.iframeDoc(f);
        if (!b.IE)j.designMode = "on";
        j.open();
        j.write("<!doctype html><html><head><title>WordPaste</title></head>");
        j.write('<body style="background-color:#FFF;font-size:12px;margin:2px;">');
        b.IE || j.write("<br />");
        j.write("</body></html>");
        j.close();
        if (b.IE)j.body.contentEditable = "true";
        f[0].contentWindow.focus()
    })
});

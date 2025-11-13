(define x (ref 10))
(free x)
(deref x) ; x is dangling, dereferencing it results in error or null.

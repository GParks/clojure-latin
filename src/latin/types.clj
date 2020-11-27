(ns latin.types)

;; hash-entries follow the form:
;;  the key to entries is the form
;;  (there may be more than one entry for a form)
;;     {:info <<parse-info>>} - see below
;;     {:inf  <<infinitive form>>} - a string
;;
;; then I need a fn that converts a ParseInfo rec into a string as below
;;     <<part of speech>> <<descr>>
;;     "[1st|2nd|3rd|3io|4th] conj. verb [1st|2nd|3rd] pers [sing.|pl.] [pres|past|future...] [act.|pass.|imp.] [ind.|subj] <<inf. form>>"
;;     "[1st|2nd|3rd] decl. noun [sing.|pl.] [nom|acc|gen|dat|abl] <<singl form>>"
;;     look for [entry] in the description to find the dictionary entry...

;; enum?

;; http://www.braveclojure.com/organization/

;; (defprotocol ParseOps
;;   string [_])

(defrecord ParseInfo [part    ; noun, verb, adj.
                      conj   ; of a verb  (1st, 2nd, 3rd, 3rd-io, i-stem, 4th )
                      decl    ; of a noun
                      plural   ; t/f
                      person    ; 1st, 2nd, 3rd
                      active     ; false = passive
                      indicitive  ; false = subjunctive
                      casum       ; (will clojure let me?) - nom., gen., acc.,
                      engl]

     Object
       ;;  learned: `(println pi)` just prints the "raw" value:
       ;;    (e.g. " #latin.types.ParseInfo{:part " ... " :engl english defn}")
       ;;  while `(str pi)` calls this fn
       (toString [s]
        (let [n (if
                    (= part :noun)
                  "noun"
                  (if
                      (= part :verb)
                   "verb"
                   "other"))
               p (cond
                    (= person :1st)  "first"
                    (= person :2nd)  "second"
                    (= person :3rd)  "third")]

           (str p " person "
                (if plural "plural " "singlular ")
                n))))


(defn test-pi [part-of-speech  ;; symbol! :noun, :verb, :adj, :adv, :prep, etc.
               plural-p        ;; false for singular (applies to both nouns and verbs, ignored for prepositions...)
              & {:keys [person    ;; :1st, :2nd or :3rd
                        conj      ;; conjugation (of a verb)
                        decl      ;; declention (of a noun or adj.)
                        active-p  ;; active or passive (verb)
                        indic-p   ;; indicitive (verb)
                        casum     ;; :nom, :gen, :accus, :___, :ablat
                        ]}
                 :or []}]
  (let [pi (->ParseInfo :verb "3rd-io" nil false :1st true true nil "english defn")
        ]
    (println "test-pi: pi = " (str pi))
  ))

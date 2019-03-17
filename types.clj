(ns latin.types)

(defrecord ParseInfo [part    ; noun, verb, adj.
                      plural   ; t/f
                      person    ; 1st, 2nd, 3rd
                      active     ; false = passive
                      indicitive  ; false = subjunctive
                      casum       ; (will clojure let me?) - nom., gen., acc.,
                      ])

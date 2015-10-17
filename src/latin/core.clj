(ns latin.core
  (:gen-class))

;;
;; http://clojure.org/cheatsheet
;; http://clojuredocs.org/clojure.core/map -
;;         "the seReturns a lazy sequence consisting of the result of applying f to the set
;;          of first items of each coll, followed by applying f to the set of second items 
;;          in each coll, until any one of the colls is exhausted.
;;

;; just a test to write Unicode to a file - it worked
;; https://clojuredocs.org/clojure.java.io/writer
;; http://docs.oracle.com/javase/7/docs/api/index.html?java/io/Writer.html

;; http://unicode-table.com/en/#latin-1-supplement

;; http://www.coniferproductions.com/2014/11/21/functional-programming-without-feeling-stupid-part-4-logic/#more-705

(defn wr-file-str [fn s]
  (with-open [of (clojure.java.io/writer fn :encoding "UTF-16")]
    (.write of s))
  )

(defn writeln-str [of s]  ; given an already open file (java.io.Writer)...
  (.write of (str s "\n")) )

(def long_a "\u0101")
(def long_i "\u012b")
(def long_e "\u0113")
(def long_o "\u014d")

;; un-tested since I re-factored this
(defn test-write [] 
  ; (wr-file-str "test.txt" (str "so this, " long_a ", is a lower case 'a' with macron"))
  )

;; append to a file test
(defn append-to-file [fn]
  (with-open [of (clojure.java.io/writer fn :encoding "UTF-16" :append true)]
    (.write of (str "at the end of the file, i w/ macron: " long_i))
    ))

(defn init-dictionary []
  (let [beginning (str "pr" long_i "ncipium")  ; an appropriate place to start
        base (str "pr" long_i "ncipi")  ; for the 3rd declention, 
        begin_gen (str base long_i)]    ; genitive is base + long i [I think]
    (wr-file-str "dictionary.txt" (str "n. " beginning ", " begin_gen " \n"))
    ))

;; ultimate goal:
;; read dictionary.txt
;;   interim - for a verb, conjugate
;;     for a noun, decline
;; read translate.txt
;;  for each word "search" for the match, print full declention or conjugation

(defn reset []
  (println "Initializing dicionary...")
  (init-dictionary)
  (println "adding entries...")
  (with-open [wof (clojure.java.io/writer "dictionary.txt" :encoding "UTF-16" :append true)]
    (let [i_say (str "d" long_i "c" long_o)
          speak_stem (str "d" long_i "ce")
          to_say (str speak_stem "re")    ; infinitive
          third_part (str "d" long_i "x" long_i)  ; I have loved - perfect indicitive
          fourth_part (str "dictum")     ; "having been loved" - supine
          
          i_do (str "ag" long_o)        ; I drive, I lead
          drive_stem (str "age")          ; to do/to lead
          to_drive (str drive_stem "re")
          do_3rd_part (str long_e "g" long_i)  ; I have done/led
          do_4th_part (str long_a "ctum")    ; having driven/done/led
          ]
      (writeln-str wof (str "v. " i_do ", " to_drive ", " do_3rd_part ", " do_4th_part))
      (writeln-str wof (str "v. " i_say ", " to_say ", " third_part ", " fourth_part))
      ) ; end let
    ) ; close (with-open)
  )

;; has-entries follow the form 
(defn interp [ln]
  ;; either conjugate (a verb) or decline (a noun)
  ;; for now, print out the whole table
  ;; ultimately create an entry for the hash-map (entry, translation)
  (println "this line = " ln)
  )

(defn print-dictionary []
  (with-open [dict (clojure.java.io/reader "dictionary.txt" :encoding "UTF-16")]
    ;; (println (clojure.string/join ";\n" (line-seq dict)))
    (doall  ; for eval of the lazy seq:
     (map interp (line-seq dict)))
    ))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; (println (clojure.string/join ", " args))
  ;; (println "\t" (count args) " command line argument(s)")
  (cond (= (first args) "init")  (reset)
        (= (first args) "output") (print-dictionary)
        true (println "default of -main cond"))
  )





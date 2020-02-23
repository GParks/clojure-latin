(ns latin.core
  (:gen-class))

; (use '[clojure.string :only (split join trim)])
(require '[clojure.string :as string])

(def long_a "\u0101")
(def long_i "\u012b")
(def long_e "\u0113")
(def long_o "\u014d")

(require 'latin.conjugate)
(require '[latin.types :as typ])

;;
;; http://clojure.org/cheatsheet
;; http://clojuredocs.org/clojure.core/map -
;;         "Returns a lazy sequence consisting of the result of applying f to the set
;;          of first items of each coll, followed by applying f to the set of second items
;;          in each coll, until any one of the colls is exhausted."
;;

;; just a test to write Unicode to a file - it worked
;; https://clojuredocs.org/clojure.java.io/writer
;; http://docs.oracle.com/javase/7/docs/api/index.html?java/io/Writer.html

;; http://unicode-table.com/en/#latin-1-supplement

;; http://www.coniferproductions.com/2014/11/21/functional-programming-without-feeling-stupid-part-4-logic/#more-705

(load-file "src/latin/file_methods.clj")

;; ultimate goal:
;; read dictionary.txt, read translate.txt, output the result of "translating"
;; (output to HTML, maybe?)
;;
;;   interim - for a verb, conjugate
;;     for a noun, decline
;; read translate.txt
;;  for each word "search" for the match, print full declention or conjugation

(defn reset []
  (println "Initializing dicionary...")
  (init-dictionary)  ; crate or re-create the file, write 'beginning'...
  (println "adding entries...")
  ;; unfortunately, with :append true and UTF-16, [clojure?] adds an extra xFE xFF
  ;; ... {I'm doing something about that, right?}
  (with-open [wof (clojure.java.io/writer "dictionary.txt" :encoding "UTF-16" :append true)]
    ;; create the entries for speak and do/drive/lead
    (let [i_say (str "d" long_i "c" long_o)
          speak_stem (str "d" long_i "ce")
          to_say (str speak_stem "re")    ; infinitive
          third_part (str "d" long_i "x" long_i)  ; I have spoken ? - perfect indicitive
          fourth_part (str "dictum")     ; "having been [loved]" - supine

          i_do (str "ag" long_o)        ; I drive, I lead
          drive_stem (str "age")          ; to do/to lead
          to_drive (str drive_stem "re")
          do_3rd_part (str long_e "g" long_i)  ; I have done/led
          do_4th_part (str long_a "ctum")]    ; having driven/done/led

      ;; write two entries
      (writeln-str wof (str "v. " i_do ", " to_drive ", " do_3rd_part ", " do_4th_part
                            ", do, drive or lead"))
      (writeln-str wof (str "v. " i_say ", " to_say ", " third_part ", " fourth_part,
                            ", speak, say or tell")))))
       ; end let
     ; close (with-open)


; (defn pr-interp-v3 [fpsai stem fpsperf-inf supine english]
  ;; [1st|2nd|3rd] pers [sing.|pl.] present active
;   (let [st1 (subs stem 0 (- (count stem) 1))
;         third "\"3rd conj. verb"
;         pai_entry_defn (str "pres. act. ind.  [" fpsai ", " stem "re]" "  - " english "\"")]
    ;; o or -m/-s/-t // -mus/-tis/-nt
    ;; theme vowel: i
;     (println "{"   fpsai      third   "1st pers sing. "  pai_entry_defn "}")
;     (println "{" (str st1 "is")   third "2nd pers sing." pai_entry_defn "}")
;     (println "{" (str st1 "it")   third "3rd pers sing." pai_entry_defn "}")
;     (println "{" (str st1 "imus") third "1st pers pl."  pai_entry_defn "}")
;     (println "{" (str st1 "itis") third "2nd pers pl."  pai_entry_defn "}")
;     (println "{" (str st1 "unt")  third "3rd pers pl."  pai_entry_defn "}")

    ;; subjunctive (-a- for 3rd conj.)
;     (let [fpsas (str (subs fpsai 0 (- (count fpsai) 1)) "am")
;           pas_entry_defn (str "pres. act. subj.  [" fpsai ", " stem "re]" "  - " english "\"")]
                                        ; 1st pers. sing. pres act. subj. =
                                        ;   drop the final "o" add "am"
;       (println "{"     fpsas     third   "1st pers. sing."  pas_entry_defn "}")
;       (println "{" (str st1 "as")  third   "2nd pers sing." pas_entry_defn  "}")
;       (println "{" (str st1 "at")   third  "3rd pers sing." pas_entry_defn  "}")
;       (println "{" (str st1 "amus")  third "1st pers pl."  pas_entry_defn  "}")
;       (println "{" (str st1 "atis")  third "2nd pers pl."  pas_entry_defn  "}")
;       (println "{" (str st1 "ant")   third "3rd pers pl."  pas_entry_defn  "}")
;       )


    ;; imperative

    ;; infinitive


    ;; passive ind.
    ;; -r/-ris-/-tur // -mur/-mini/-ntur

    ;; passive subj.
;     )
;  )


;; given a line fr. a dictionary file (a lexicon entry),
;; return a list of "entries" -- a map entry where the key is a word,
;;  and the value is the
(defn interp [ln]
  (let [ln2 (if (= (int (first ln)) 65279) (subs ln 1) ln)
                                   ; writing *another* line to a file,
                                   ;  or the first line
                                   ; includes a (??) BOF marker
        ws (string/split ln2 #",")  ; words
        fw (first ws)
        d (+ (.indexOf fw ".") 1)  ; where is the dot
        pos (subs fw 0 d)   ; part of speech
        w1 (subs fw (+ d 1))]
      (cond
        (= pos "v.")
        (apply latin.conjugate/interp-verb w1 (map string/trim (rest ws)))
        (= pos "n.")
        ())))


(defn pr-interp-v
    "now moved to conjugate - no longer pr- to output"
  [a b c d e])


(defn pr-interp [ln]
  ;; either conjugate (a verb) or decline (a noun)
  ;; --for now, print out the whole table--
  ;; ultimately create an entry for the hash-map (entry, translation)
  (let [ln2 (if (= (int (first ln)) 65279) (subs ln 1) ln)
        ; possibly remove leading 00xFEFF
        d (+ (.indexOf ln2 ".") 1)   ; find the dot (in "n." or "v." or even "adj.")
        pos (subs ln2 0 d)   ; Part Of Speech
        c1 (.indexOf ln2 "," d)   ; find the first comma
        w1 (subs ln2 (+ d 1) c1)]

    (println "this line = " ln)
    ;; (println "\tpart of speech =" pos)
    ;; (println (str "\tword 2 is '" w2 "'"))
    (cond
      (= pos "v.")
      ;; process a verb
      (let [c2 (.indexOf ln2 "," (+ c1 1))  w2 (subs ln2 (+ c1 2) c2)
            c3 (.indexOf ln2 "," (+ c2 1))  w3 (subs ln2 (+ c2 2) c3)
            c4 (.indexOf ln2 "," (+ c3 1))  w4 (subs ln2 (+ c3 2) c4)
            e (subs ln2 (+ c4 2))]

        ;; (println "\t''last'' comma at" c4)
        (pr-interp-v w1 w2 w3 w4 e))

      (= pos "n.")
      (println "\ta noun")
      true
      (println "unknown part of speech, '" pos "'"))))



(defn add-word [d es])
  ; given a dictionary and
  ; a list of entries
  ; insert these entries into the dictionary and return the [new] dictionary


(defn load-dictionary
  ([d]
   (with-open [dict (clojure.java.io/reader "dictionary.txt" :encoding "UTF-16")]
     ;; (println (clojure.string/join ";\n" (line-seq dict)))
     (doall  ; for eval of the lazy seq:
      ;;(map pr-interp (line-seq dict)))
      (reduce add-word d
        (map
          interp
          (line-seq dict))))))


     ;; if called with no arguments, re-call load-dict with initial, empty dictionary
  ([] (load-dictionary (hash-map))))

(defn translate [filename]
    (println "filename = " filename)
    (load-dictionary))


(defn print-dictionary
  []
  (println "this will be print-dictionary... soon!"))


(defn -main
  "init a latin dictionary file; output, or (eventually) interpret."
  [& args]
  (let [f (first args)])
  ;; (println (clojure.string/join ", " args))
  ;; (println "\t" (count args) " command line argument(s)")
  (cond (= f "init")  (reset)
        (= f "output") (do
                        (load-dictionary)
                        (print-dictionary))
        (= f "interpret") (translate (rest args))
        (= f "test") (typ/test-pi)
        true (println "default of -main cond\n\ttry 'init' or 'output'; better 'interpret [file]'")))

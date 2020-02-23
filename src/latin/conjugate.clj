(ns latin.conjugate)

(use '[clojure.string :only (join)])

(defn interp-v3 [fpsai stem fpsperf-inf supine english]
  ;; [1st|2nd|3rd] pers [sing.|pl.] present active
  (let [st1 (subs stem 0 (- (count stem) 1))
        third "\"3rd conj. verb"
        pai_entry_defn (str "pres. act. ind.  [" fpsai ", " stem "re]" "  - " english "\"")]
    ;; o or -m/-s/-t // -mus/-tis/-nt
    ;; theme vowel: i
    (println "{"   fpsai      third   "1st pers sing. "  pai_entry_defn "}")
    (println "{" (str st1 "is")   third "2nd pers sing." pai_entry_defn "}")
    (println "{" (str st1 "it")   third "3rd pers sing." pai_entry_defn "}")
    (println "{" (str st1 "imus") third "1st pers pl."  pai_entry_defn "}")
    (println "{" (str st1 "itis") third "2nd pers pl."  pai_entry_defn "}")
    (println "{" (str st1 "unt")  third "3rd pers pl."  pai_entry_defn "}")

    ;; subjunctive (-a- for 3rd conj.)
    (let [fpsas (str (subs fpsai 0 (- (count fpsai) 1)) "am")
          pas_entry_defn (str "pres. act. subj.  [" fpsai ", " stem "re]" "  - " english "\"")]
                                        ; 1st pers. sing. pres act. subj. =
                                        ;   drop the final "o" add "am"
      (println "{"     fpsas     third   "1st pers. sing."  pas_entry_defn "}")
      (println "{" (str st1 "as")  third   "2nd pers sing." pas_entry_defn  "}")
      (println "{" (str st1 "at")   third  "3rd pers sing." pas_entry_defn  "}")
      (println "{" (str st1 "amus")  third "1st pers pl."  pas_entry_defn  "}")
      (println "{" (str st1 "atis")  third "2nd pers pl."  pas_entry_defn  "}")
      (println "{" (str st1 "ant")   third "3rd pers pl."  pas_entry_defn  "}"))))



    ;; imperative

    ;; infinitive


    ;; passive ind.
    ;; -r/-ris-/-tur // -mur/-mini/-ntur

    ;; passive subj.




(defn interp-3io [fpsai stem fpsperf-inf supine english])


(defn interp-v4 [fpsai stem fpsperf-inf supine english])


(defn interp-v1 [fpsai stem fpsperf-inf supine english])


(defn interp-v2 [fpsai stem fpsperf-inf supine english])




(defn interp-verb [fpsai pres-inf fpsperf-inf supine & english]
  (let [e (subs fpsai (- (count fpsai) 2))   ; "end" of 1st sing active ind.
        stem (subs pres-inf 0 (max 0 (- (count pres-inf) 2)))   ;; remove "re" fr pres-inf to reveal the stem
        io (str "i" latin.core/long_o)
        desc (join ", " english)]
    ;; (println (str "I " desc "; stem is '" stem "'"))
    ;; (println "\t inf:" fpsperf-inf "; supine:" supine)
    ;; println separates its arguments with a space; str does not


    (case  (subs stem (- (count stem) 1))
     long_a
      (interp-v1 fpsai stem fpsperf-inf supine desc)
     long_e
      (interp-v2 fpsai stem fpsperf-inf supine desc)
    ;; otherwise
     (if
       (= e io)
       (if (= (subs stem (- (count stem) 1)) "e")
         (interp-3io fpsai stem fpsperf-inf supine desc)
         ;; should check if long_i before "assuming" 4th conj.
         (interp-v4 fpsai stem fpsperf-inf supine desc))
         ;; *not* -io, so not 3rd-io or 4th
       (interp-v3 fpsai stem fpsperf-inf supine desc)))))
        ; end if, end case

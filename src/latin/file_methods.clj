
;; Note: this file is *loaded* ("(load-file ... ") as opposed to "required"
;; three methods 'available for use':
;;  - writeln-str
;;      assuming an already open java.io.Writer, write a new string *with newline*
;;  - (wr-file-str)
;;      given a filename, open and write a new string, *initializing* the new file
;;      used by init-dictionary and, kludgy, by reset to add entries
;;
;;  - init-dictionary
;;      initialize a new dictionary file with the entry for "the beginning"
;;

;; append to a file test
(defn append-to-file [filename]
  (with-open [of (clojure.java.io/writer filename :encoding "UTF-16" :append true)]
    (.write of (str "at the end of the file, i w/ macron: " long_i))))



;; To Do: figure out why I get extraneos BOF (unicode) marker(s)...
(defn writeln-str
    "with an already open file (java.io.Writer), add string s as a new line (dictionary entry)"
  [of s]  ; given an already open file (java.io.Writer)...
  (.write of (str s "\n")))

(defn wr-file-str
    "open file named filename, write string - overwrites file!"
  [filename s]
  (with-open [of (clojure.java.io/writer filename :encoding "UTF-16")]
    (.write of s)))

;; !! N.B.  Use with caution!  This completely *initializes* a new dictionary.txt
;;   file, creating a (single!) new entry: the noun principium, principi
(defn init-dictionary
    "initialize the dictionary.txt file"
  []
  (let [beginning (str "pr" long_i "ncipium")  ; an appropriate place to start
        base (str "pr" long_i "ncipi")  ; for the 2nd declension (m. and n.)
        begin_gen (str base long_i)]    ; genitive is base + long i
    (wr-file-str "dictionary.txt" (str "n. " beginning ", " begin_gen ", beginning \n"))))

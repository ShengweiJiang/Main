## P1Q1
We are given the following corpus, modified from the one in the chapter:
\<s> I am Sam </s>
\<s> Sam I am </s>
\<s> I am Sam </s>
\<s> I do not like green eggs and Sam </s>
Using a bigram language model with add-one smoothing, what is P(Sam|am)? Include <s> and </s> in your counts just like any other token.  
|char|count|
|----|-----|
|I|4|
|am|3
|do|1
|not|1
|like|1
|green|1
|eggs|1
|and|1
|Sam|4
|\<s>|1
|\</s>|1 

P(Sam|am) with add one = c(Sam,am)+1/c(am)+V = 2+1/3+11=3/14=0.2143  

## P2Q1&Q2
unique words = 41738  
word tokens = 2468210
## P2Q3
percentage of unseen tokens = 1.6612495485734922%  
percentage of unseen words = 3.6057692307692304%
## P2Q4
percentage of bigrams type = 25.869565217391305%  
percentage of bigrams token = 22.367928062944923%

## P2Q5
### unigram
the parameters are: log(p(\<s>)) + log(p(i)) + log(p(look)) + log(p(forward)) + log(p(to)) + log(p(hearing)) + log(p(your)) + log(p(reply)) + log(p(.)) + log(p(\</s>))
the log probability is: log(0.03893762581720342) + log(0.0028576323587245593) + log(0.000238687646259457) + log(0.00018456434637354423) + log(0.02065563174351007) + log(8.137963795795515e-05) + log(0.00047387090619536564) + log(5.061891356236445e-06) + log(0.03422383683577278) + log(0.03893762581720342) = -94.93878209357644
the perplexity of given string is: 721.0113746656128

## P2Q6
perplexity of unigram maximum likelihood model = 721.0113746656128  
perplexity of bigram maximum likelihood model = infinity
perplexity of bigram model with Add-One smoothing = 1774.6077550851912
## P2Q7

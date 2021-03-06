package org.elasticsearch.index.query;

import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Explanation;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Weight;

import java.io.IOException;
import java.util.Set;

public class PositionMatchWeight extends Weight {
    final Weight weight;

    PositionMatchWeight(Query query, Weight weight) {
        super(query);
        this.weight = weight;
    }

    @Override
    public void extractTerms(Set<Term> terms) {
        weight.extractTerms(terms);
    }

    @Override
    public Explanation explain(LeafReaderContext context, int docID) throws IOException {
        return scorer(context).explain(docID);
    }

    @Override
    public PositionMatchScorer scorer(LeafReaderContext context) throws IOException {
        return new PositionMatchScorer(this, context);
    }

    @Override
    public boolean isCacheable(LeafReaderContext context) {
        return false; //weight.isCacheable(context);
    }
}

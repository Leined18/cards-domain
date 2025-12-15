package com.health.tools.cards.domain.service.prototype;

import com.health.tools.cards.domain.model.CardType;
import com.health.tools.cards.domain.model.details.CardDetails;
import com.health.tools.cards.domain.model.details.biomagneticpair.BiomagneticPairDetails;
import org.springframework.stereotype.Component;

@Component
public final class BiomagneticPairPrototype implements CardPrototype {

    @Override
    public CardType supportedType() {
        return CardType.BIOMAGNETIC_PAIR;
    }

    @Override
    public CardDetails createDetails() {
        return new BiomagneticPairDetails();
    }
}

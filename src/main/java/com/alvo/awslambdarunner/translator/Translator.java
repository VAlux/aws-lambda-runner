package com.alvo.awslambdarunner.translator;

import java.util.Optional;

public interface Translator<I, O> {
  Optional<O> from(I input);
}

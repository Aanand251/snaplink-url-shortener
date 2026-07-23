import { motion } from "framer-motion";

import ClayButton from "./ClayButton";
import ClayCard from "./ClayCard";

function EmptyState({
                        icon: Icon,
                        title,
                        description,
                        buttonText,
                        onButtonClick,
                    }) {
    return (
        <ClayCard>
            <motion.div
                initial={{ opacity: 0, y: 12 }}
                animate={{ opacity: 1, y: 0 }}
                transition={{ duration: 0.35 }}
                className="flex flex-col items-center justify-center py-14 text-center"
            >
                {Icon && (
                    <div
                        className="
                            mb-6
                            flex
                            h-20
                            w-20
                            items-center
                            justify-center
                            rounded-3xl
                            bg-cyan-400/10
                            text-cyan-300
                        "
                    >
                        <Icon className="h-10 w-10" />
                    </div>
                )}

                <h3 className="text-2xl font-semibold text-white">
                    {title}
                </h3>

                <p className="mt-3 max-w-md text-sm leading-7 text-zinc-400">
                    {description}
                </p>

                {buttonText && (
                    <div className="mt-8">
                        <ClayButton
                            variant="primary"
                            onClick={onButtonClick}
                        >
                            {buttonText}
                        </ClayButton>
                    </div>
                )}
            </motion.div>
        </ClayCard>
    );
}

export default EmptyState;
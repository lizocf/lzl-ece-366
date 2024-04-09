// custom hook
import {useState, useEffect} from "react";
import { getAuth, onAuthStateChanged} from "firebase/auth";

const useUser = () => {
    const [user, setUser] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(getAuth(), user => {
            setUser(user);
            setIsLoading(false);
        });

        return unsubscribe; // if user navigates away, removes hook
    }, []); // only calls this when auth; only once

    return {user, isLoading};
};

export default useUser;
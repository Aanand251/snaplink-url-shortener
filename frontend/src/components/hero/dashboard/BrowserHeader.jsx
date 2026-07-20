function BrowserHeader() {
    return (
        <div className="flex items-center justify-between">

            <div className="flex gap-2">

                <span className="h-3 w-3 rounded-full bg-red-400"/>

                <span className="h-3 w-3 rounded-full bg-yellow-400"/>

                <span className="h-3 w-3 rounded-full bg-green-400"/>

            </div>

            <div className="rounded-full border border-white/10 bg-white/5 px-5 py-2 text-sm text-zinc-400">
                dashboard.snaplink.app
            </div>

        </div>
    );
}

export default BrowserHeader;